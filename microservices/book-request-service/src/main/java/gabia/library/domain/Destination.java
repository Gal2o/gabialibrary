package gabia.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Destination {
    Headquarter("본사"),
    SEOCHO("서초IDC"),
    GASAN("가산IDC"),
    WCENTER("가산W센터");

    private final String destination;
}
