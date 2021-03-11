package gabia.library.dto;

import gabia.library.domain.rent.RentStatus;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentResponseDto {

    private Long id;

    private Long bookId;

    private String bookTitle;

    private String bookAuthor;

    private String identifier;

    private RentStatus rentStatus;

}
