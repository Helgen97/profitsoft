package dev.profitsoft.dto;

import dev.profitsoft.data.BookData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookShortDto {

    private String id;
    private String authorName;
    private String bookName;

    public static BookShortDto bookShortDtoFromData(BookData bookData) {
        return BookShortDto.builder()
                .id(bookData.get_id())
                .bookName(bookData.getBookName())
                .authorName(bookData.getAuthorName())
                .build();
    }
}
