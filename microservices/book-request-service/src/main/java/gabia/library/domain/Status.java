package gabia.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    REQUESTED("REQUESTED"),
    CANCELED("CANCELED"),
    BUYING("BUYING"),
    COMPLETED("COMPLETED");

    private final String status;
}
