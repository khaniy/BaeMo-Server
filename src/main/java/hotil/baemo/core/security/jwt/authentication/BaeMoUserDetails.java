package hotil.baemo.core.security.jwt.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface BaeMoUserDetails extends UserDetails {
    Long userId();

    String name();

    String profile();

    String realName();
}