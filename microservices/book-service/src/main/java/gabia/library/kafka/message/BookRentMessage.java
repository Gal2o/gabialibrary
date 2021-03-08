package gabia.library.kafka.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import gabia.library.domain.book.BookAlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRentMessage {

    private Long bookId;
    private String identifier;
    private String bookTitle;
    private String bookAuthor;
    private BookAlertType bookAlertType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate rentExpiredDate;

}
