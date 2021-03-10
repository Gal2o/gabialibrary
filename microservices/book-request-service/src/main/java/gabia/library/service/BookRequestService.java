package gabia.library.service;

import gabia.library.config.NaverConfig;
import gabia.library.dto.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BookRequestService {

    private HttpEntity<?> headers;
    private final NaverConfig naverConfig;
    private RestTemplate restTemplate = new RestTemplate();

    public List<Object> getBookRequest() {
        String url = "https://openapi.naver.com/v1/search/book?query=";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", naverConfig.getClientId());
        requestHeaders.put("X-Naver-Client-Secret", naverConfig.getSecretId());
        this.headers = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> responseBody = restTemplate.exchange(url, HttpMethod.GET, headers, String.class);
        System.out.println(responseBody);
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();

        return null;
    }
}
