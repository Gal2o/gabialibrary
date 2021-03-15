package gabia.library.service;

import gabia.library.config.NaverConfig;
import gabia.library.domain.BookRequest;
import gabia.library.domain.BookRequestRepository;
import gabia.library.domain.Destination;
import gabia.library.domain.Status;
import gabia.library.dto.BookRequestDto;
import gabia.library.dto.NaverBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BookRequestService {

    private HttpEntity<?> headers;
    private final NaverConfig naverConfig;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final BookRequestRepository bookRequestRepository;

    public BookRequestDto addBookRequest(BookRequestDto bookRequestDto) {
        BookRequest bookRequest = bookRequestRepository.save(BookRequest.builder()
                .userId(bookRequestDto.getUserId())
                .bookName(bookRequestDto.getBookName())
                .author(bookRequestDto.getAuthor())
                .destination(bookRequestDto.getDestination())
                .url(bookRequestDto.getUrl())
                .status(Status.REQUESTED)
                .build());

        return modelMapper.map(bookRequest, BookRequestDto.class);
    }

    public NaverBook getBookByNaverApi(String title, Long page) {

        String url = naverConfig.geturl(title, page);

        MultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<>();
        requestHeaders.add("X-Naver-Client-Id", naverConfig.getClientId());
        requestHeaders.add("X-Naver-Client-Secret", naverConfig.getSecretId());

        this.headers = new HttpEntity<>(requestHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, headers, NaverBook.class).getBody();
    }

    public List<BookRequestDto> findAll(){
        return bookRequestRepository.findAll()
                .stream()
                .map(bookRequest -> modelMapper.map(bookRequest, BookRequestDto.class))
                .collect(Collectors.toList());
    }

    public BookRequestDto confirmBookRequest(Long id) {
        //TODO: 예외 추가
        BookRequest bookRequest = bookRequestRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException());

        bookRequest.update();

        return modelMapper.map(bookRequest, BookRequestDto.class);
    }

    public BookRequestDto cancelBookRequest(Long id) {
        //TODO: 예외 추가
        BookRequest bookRequest = bookRequestRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException());

        bookRequest.remove();

        return modelMapper.map(bookRequest, BookRequestDto.class);
    }
}
