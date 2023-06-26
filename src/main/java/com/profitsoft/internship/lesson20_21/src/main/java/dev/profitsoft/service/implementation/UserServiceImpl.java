package dev.profitsoft.service.implementation;

import dev.profitsoft.data.BookData;
import dev.profitsoft.data.UserData;
import dev.profitsoft.data.UserRole;
import dev.profitsoft.dto.BookShortDto;
import dev.profitsoft.dto.CreateUserDto;
import dev.profitsoft.dto.UserFullDto;
import dev.profitsoft.exception.NotFoundException;
import dev.profitsoft.repository.implementation.UserRepo;
import dev.profitsoft.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String createUser(CreateUserDto newUserDto) {
        String rawPassword = UUID.randomUUID().toString();
        UserData userData = getUserDataFromCreateDto(newUserDto, rawPassword);
//      send message to other service using Kafka with user password and email
        return userRepository.createUser(userData);
    }

    private UserData getUserDataFromCreateDto(CreateUserDto newUserDto, String rawPassword) {
        return UserData.builder()
                .email(newUserDto.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .userRole(UserRole.USER)
                .userBooks(Collections.emptyList())
                .build();
    }

    @Override
    public UserFullDto findUserByEmail(String email) {
        UserData user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email %s not found".formatted(email)));
        return UserFullDto.userFullDtoFromData(user);
    }


}
