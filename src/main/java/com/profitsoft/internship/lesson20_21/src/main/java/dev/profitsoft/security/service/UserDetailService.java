package dev.profitsoft.security.service;

import dev.profitsoft.data.UserData;
import dev.profitsoft.repository.implementation.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserData> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            UserBuilder builder = null;
            UserData authorisedUser = user.get();

            builder = User.withUsername(authorisedUser.getEmail());
            builder.password(authorisedUser.getPassword());
            builder.roles(authorisedUser.getUserRole().toString());

            return builder.build();
        }

        throw new UsernameNotFoundException(email + "not found!");
    }
}
