package gabia.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    REQUESTED("요청중"),
    CANCELED("취소"),
    BUYING("구매중"),
    COMPLETED("구매완료");

    private final String status;
}
