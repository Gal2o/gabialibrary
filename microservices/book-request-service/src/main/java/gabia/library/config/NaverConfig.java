package gabia.library.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class NaverConfig {

    private final String clientId = "gShmfRvqAqrfPscaLisH";
    private final String secretId = "_MBNBX9tOR";
    private final String url = "https://openapi.naver.com/v1/search/book_adv?d_titl=";

    public String geturl(String title){
        return url + title;
    }
}
