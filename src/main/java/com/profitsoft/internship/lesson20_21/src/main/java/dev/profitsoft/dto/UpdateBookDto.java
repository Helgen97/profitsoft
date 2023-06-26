package dev.profitsoft.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateBookDto {

    private String bookId;
    private List<String> usersId;

}
