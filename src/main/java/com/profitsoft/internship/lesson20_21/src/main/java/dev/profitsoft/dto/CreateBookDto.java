package dev.profitsoft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateBookDto {

    private String authorName;
    private String bookName;
    private String bookText;

}
