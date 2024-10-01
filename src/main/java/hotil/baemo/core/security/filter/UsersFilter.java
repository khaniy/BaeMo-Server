package hotil.baemo.core.security.filter;

import hotil.baemo.core.logging.LoggingFilter;
import hotil.baemo.core.security.BaseSecurity;
import hotil.baemo.core.security.jwt.authentication.manager.BaeMoAuthenticationManager;
import hotil.baemo.core.security.jwt.authentication.manager.SocialAuthenticationManager;
import hotil.baemo.core.security.jwt.filter.JwtFilter;
import hotil.baemo.core.security.jwt.login.BaeMoLoginFilter;
import hotil.baemo.core.security.jwt.login.SocialLoginFilter;
import hotil.baemo.core.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersFilter {
    private static final String USERS_PREFIX_API = "/api/users";
    private static final String API = USERS_PREFIX_API + "/**";
    private static final String BAEMO_LOGIN_URL = USERS_PREFIX_API + "/login";
    private static final String SOCIAL_LOGIN_URL = USERS_PREFIX_API + "/social/login";

    private static final String[] ALLOW_LIST = {
        USERS_PREFIX_API + "/join",
        USERS_PREFIX_API + "/social/join",
        BAEMO_LOGIN_URL, SOCIAL_LOGIN_URL,
        USERS_PREFIX_API + "/social/login",
        USERS_PREFIX_API + "/phone/sign-up",
        USERS_PREFIX_API + "/phone/authentication",
        USERS_PREFIX_API + "/phone/find/password",
        USERS_PREFIX_API + "/phone/find/password/authentication",
    };

    private static final String[] AUTHENTICATED = {
    };

    private final BaseSecurity baseSecurity;
    private final JwtFilter jwtFilter;

    private final BaeMoAuthenticationManager baeMoAuthenticationManager;
    private final SocialAuthenticationManager socialAuthenticationManager;
    private final JwtUtil jwtUtil;
    private final LoggingFilter loggingFilter;

    public SecurityFilterChain doFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(API)
            .with(baseSecurity, BaseSecurity::active)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(ALLOW_LIST).permitAll()
                .requestMatchers(AUTHENTICATED).authenticated()
                .anyRequest().authenticated()
            )

            .addFilter(new BaeMoLoginFilter(baeMoAuthenticationManager, jwtUtil, BAEMO_LOGIN_URL))
            .addFilter(new SocialLoginFilter(socialAuthenticationManager, jwtUtil, SOCIAL_LOGIN_URL))
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .addFilterBefore(loggingFilter, JwtFilter.class)
            .build();
    }
}