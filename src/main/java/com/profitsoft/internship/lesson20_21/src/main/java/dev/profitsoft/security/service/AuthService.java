package dev.profitsoft.security.service;

import dev.profitsoft.data.UserData;
import dev.profitsoft.dto.UserFullDto;
import dev.profitsoft.repository.implementation.UserRepo;
import dev.profitsoft.security.data.AuthTokenData;
import dev.profitsoft.security.data.AuthUser;
import dev.profitsoft.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepo userRepository;

    public AuthTokenData geTokenForAuthorizedUser(Authentication authentication) {
        String token = jwtService.getToken(authentication.getName());

        return new AuthTokenData(token);
    }

    public void authenticateUser(String token) {
        String userEmail = jwtService.getAuthUser(token);
        UserFullDto userFromDB = getUserFullDtoFromDB(userEmail);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                        getAuthUserDetails(userFromDB),
                        null,
                        List.of(new SimpleGrantedAuthority(userFromDB.getUserRole().toString()))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserFullDto getUserFullDtoFromDB(String userEmail) {
        UserData userData = userRepository.findByEmail(userEmail).get();

        return UserFullDto.userFullDtoFromData(userData);
    }

    private AuthUser getAuthUserDetails(UserFullDto user) {

        return AuthUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }



}
