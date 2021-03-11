package gabia.library.service;

import gabia.library.config.NaverConfig;
import gabia.library.dto.BookRequestDto;
import gabia.library.dto.NaverBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class BookRequestService {

    private HttpEntity<?> headers;
    private final NaverConfig naverConfig;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    public NaverBook getBookByNaverApi(String title) {

        String url = naverConfig.geturl(title);

        MultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<>();
        requestHeaders.add("X-Naver-Client-Id", naverConfig.getClientId());
        requestHeaders.add("X-Naver-Client-Secret", naverConfig.getSecretId());

        this.headers = new HttpEntity<>(requestHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, headers, NaverBook.class).getBody();
    }
}
