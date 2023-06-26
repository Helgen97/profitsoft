package dev.profitsoft.service.interfaces;

import dev.profitsoft.dto.CreateUserDto;
import dev.profitsoft.dto.UserFullDto;

public interface UserService {

    String createUser(CreateUserDto newUserDto);

    UserFullDto findUserByEmail(String email);

}
