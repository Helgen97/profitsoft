package dev.profitsoft.dto;

import dev.profitsoft.data.UserData;
import dev.profitsoft.data.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserFullDto {

    private String id;
    private String email;
    private UserRole userRole;
    private List<BookShortDto> userBooks;

    public static UserFullDto userFullDtoFromData(UserData user) {
        return UserFullDto.builder()
                .id(user.get_id())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .userBooks(user.getUserBooks().stream()
                        .map(BookShortDto::bookShortDtoFromData)
                        .collect(Collectors.toList()))
                .build();
    }

}
