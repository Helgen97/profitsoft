package dev.profitsoft.security.controller;

import dev.profitsoft.security.data.AccountCredential;
import dev.profitsoft.security.data.AuthTokenData;
import dev.profitsoft.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthTokenData login(@RequestBody AccountCredential accountCredential) {
        Authentication authentication = authenticateUser(accountCredential);
        return authService.geTokenForAuthorizedUser(authentication);
    }

    private Authentication authenticateUser(AccountCredential accountCredential) {
        UsernamePasswordAuthenticationToken credential = new UsernamePasswordAuthenticationToken(
                accountCredential.getEmail(),
                accountCredential.getPassword());

        return authenticationManager.authenticate(credential);
    }

}
