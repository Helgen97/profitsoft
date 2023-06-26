package dev.profitsoft.configuration;

import dev.profitsoft.data.UserData;
import dev.profitsoft.data.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@Configuration
public class StartUpConfiguration {

    @Bean
    public CommandLineRunner runner(final MongoTemplate mongoTemplate,
                                    final BCryptPasswordEncoder passwordEncoder) {

        return args -> {
            mongoTemplate.dropCollection(UserData.class);

            mongoTemplate.save(UserData.builder()
                    .email("admin@profitsoft.dev")
                    .password(passwordEncoder.encode("admin"))
                    .userRole(UserRole.ADMIN)
                    .userBooks(Collections.emptyList())
                    .build());

            mongoTemplate.save(UserData.builder()
                    .email("user@profitsoft.dev")
                    .password(passwordEncoder.encode("user"))
                    .userRole(UserRole.USER)
                    .userBooks(Collections.emptyList())
                    .build());
        };
    }
}
