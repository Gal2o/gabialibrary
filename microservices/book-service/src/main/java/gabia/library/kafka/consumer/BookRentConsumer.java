package gabia.library.kafka.consumer;

import gabia.library.domain.book.Book;
import gabia.library.domain.book.BookRepository;
import gabia.library.domain.rent.Rent;
import gabia.library.domain.rent.RentRepository;
import gabia.library.exception.EntityNotFoundException;
import gabia.library.kafka.BookRentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gabia.library.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookRentConsumer {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;

    /**
     * add rent and update book consumer
     */
    @Transactional
    public void addRentAndUpdateBook(BookRentMessage message) {
        Book book = bookRepository.findByIdAndIsDeleted(message.getBookId(), false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        Rent rent = rentRepository.save(book.toRent(message.getIdentifier(), message.getRentExpiredDate()));

        book.rent(rent.getIdentifier(), rent.getId());
    }

}
