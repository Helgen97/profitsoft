package dev.profitsoft.security.data;

import dev.profitsoft.data.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthUser {

    private String id;
    private String email;

}
