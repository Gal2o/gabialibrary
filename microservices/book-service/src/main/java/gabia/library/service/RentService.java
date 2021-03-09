package gabia.library.service;

import gabia.library.domain.book.Book;
import gabia.library.domain.book.BookRepository;
import gabia.library.domain.rent.Rent;
import gabia.library.domain.rent.RentRepository;
import gabia.library.dto.BookResponseDto;
import gabia.library.dto.RentResponseDto;
import gabia.library.dto.UserEmailDto;
import gabia.library.exception.*;
import gabia.library.kafka.channel.BookRentOutputChannel;
import gabia.library.kafka.channel.BookReturnOutputChannel;
import gabia.library.kafka.publisher.MessagePublisher;
import gabia.library.mapper.BookMapper;
import gabia.library.mapper.RentMapper;
import gabia.library.utils.page.PageUtils;
import gabia.library.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static gabia.library.exception.message.BookExceptionMessage.*;
import static gabia.library.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;
import static java.util.Objects.isNull;

@EnableBinding({BookRentOutputChannel.class, BookReturnOutputChannel.class})
@Slf4j
@RequiredArgsConstructor
@Service
public class RentService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final PageUtils pageUtils;
    private final MessagePublisher messagePublisher;
    private final RestTemplate restTemplate;

    private static final int RENT_PAGE_SIZE = 10;
    private static final int RENT_SCALE_SIZE = 10;
    private final static String GET_AUTH_USER_URL = "http://user-service/users?identifier=";

    @Transactional
    public RentResponseDto rentBook(Long id, String identifier) {
        UserEmailDto userEmailDto = getUserEmailByIdentifier(identifier).getBody();

        if (isNull(userEmailDto)) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND);
        }

        Book book = bookRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (book.isRent()) {
            throw new AlreadyRentException(ALREADY_RENT);
        }

        /**
         * send rent book event to kafka
         */
        messagePublisher.publishBookRentMessage(book.toBookRentMessage(identifier, userEmailDto.getEmail()));

        return RentMapper.INSTANCE.bookToRentResponseDto(book);
    }

    @Transactional
    public BookResponseDto extendRent(Long bookId, Long rentId, String identifier) {
        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        if (book.getExtensionCount() >= 3) {
            throw new ExtensionCountException(INVALID_EXTENSION_COUNT_VALUE);
        }

        book.extendRent();

        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (rent.isInvalidStatus()) {
            throw new InvalidRentStatusException(INVALID_RENT_STATUS_EXCEPTION);
        }

        rent.extendRent(book);

        return BookMapper.INSTANCE.bookToBookAndRentResponseDto(book, rent);
    }

    @Transactional
    public BookResponseDto returnBook(Long bookId, Long rentId, String identifier) {
        UserEmailDto userEmailDto = getUserEmailByIdentifier(identifier).getBody();

        if (isNull(userEmailDto)) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND);
        }

        Book book = bookRepository.findByIdAndIsDeleted(bookId, false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (isInValidIdentifier(book.getIdentifier(), identifier)) {
            throw new InvalidIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        /**
         * send return book event to kafka
         */
        messagePublisher.publishBookReturnMessage(book.toBookReturnMessage(identifier, userEmailDto.getEmail()));

        return BookMapper.INSTANCE.bookToBookAndRentResponseDto(book, rent);
    }

    public PagingResponseDto getRentListOfUser(String identifier, Integer page) {

        Page<Rent> rentPage = rentRepository.findAllByIdentifier(identifier, pageUtils.getPageable(page, RENT_PAGE_SIZE, Sort.Direction.DESC, "createdDate"));

        List<RentResponseDto> rentResponseDtoList = rentPage.stream().map(RentMapper.INSTANCE::rentToRentResponseDto).collect(Collectors.toList());

        return pageUtils.getCommonPagingResponseDto(rentPage, rentResponseDtoList, RENT_SCALE_SIZE);
    }

    private boolean isInValidIdentifier(String identifierFromEntity, String identifier) {
        return Optional.ofNullable(identifierFromEntity)
                .map(val -> !val.equals(identifier))
                .orElse(true);
    }

    public ResponseEntity<UserEmailDto> getUserEmailByIdentifier(String identifier) {
        return restTemplate.getForEntity(GET_AUTH_USER_URL + identifier, UserEmailDto.class);
    }

}
