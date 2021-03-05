package gabia.library.dto;

import gabia.library.utils.page.PageResponseData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookPagingResponseDto {

    private List<BookResponseDto> bookResponseDtoList;
    private PageResponseData pageResponseData;

    @Builder
    public BookPagingResponseDto(List<BookResponseDto> bookResponseDtoList, PageResponseData pageResponseData) {
        this.bookResponseDtoList = bookResponseDtoList;
        this.pageResponseData = pageResponseData;
    }

}
