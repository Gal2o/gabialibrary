package gabia.library.dto;

import gabia.library.domain.Destination;
import gabia.library.domain.Status;
import gabia.library.exception.BusinessException;
import gabia.library.kafka.BookRequestMessage;
import gabia.library.utils.alert.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBookRequestDto {

    private String identifier;
    private String email;

    public BookRequestMessage toBookRequestMessage(BookRequestDto bookRequestDto) {

        return BookRequestMessage.builder()
                .identifier(identifier)
                .email(email)
                .bookTitle(bookRequestDto.getBookName())
                .bookAuthor(bookRequestDto.getAuthor())
                .destination(getDestinationValue(bookRequestDto.getDestination().value()))
                .requestState(Status.REQUESTED.value())
                .alertType(AlertType.REQUESTED)
                .build();
    }

    private String getDestinationValue(String destinationVal) {
        String destination = "";

        if (destinationVal.equals("HEAD")) {
            destination = "본사";
        } else if (destinationVal.equals("SEOCHO")) {
            destination = "서초IDC";
        } else if (destinationVal.equals("GASAN")) {
            destination = "가산IDC";
        } else if (destinationVal.equals("WCENTER")) {
            destination = "가산W센터";
        } else {
            throw new BusinessException("올바르지 않은 목적지 입니다.");
        }

        return destination;
    }
}
