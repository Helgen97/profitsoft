package dev.profitsoft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookFullDto {

    private String id;
    private String authorName;
    private String bookName;
    private String bookText;
    private List<UserShortDto> bookOwners;

}
