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
public class CommunityFilter {
    private static final String COMMUNITY_PREFIX_API = "/api/communities";
    private static final String API = COMMUNITY_PREFIX_API + "/**";

    private static final String[] ALLOW_LIST = {
        COMMUNITY_PREFIX_API + "/category",
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

            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .addFilterBefore(loggingFilter, JwtFilter.class)
            .build();
    }
}