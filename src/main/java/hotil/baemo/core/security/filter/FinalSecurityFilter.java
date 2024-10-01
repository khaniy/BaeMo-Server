package hotil.baemo.core.security.filter;


import hotil.baemo.config.socket.properties.WebSocketProperties;
import hotil.baemo.core.security.BaseSecurity;
import hotil.baemo.core.security.jwt.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalSecurityFilter {

    private static final String WILDCARD_URL = "/**";
    private final BaseSecurity baseSecurity;
    private final JwtFilter jwtFilter;

    public SecurityFilterChain doFilterChain(HttpSecurity http) throws Exception {
        return http
       .securityMatcher(WILDCARD_URL)
                .with(baseSecurity, BaseSecurity::active)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/responseCode").permitAll()
                        .requestMatchers("/api/notification/test").permitAll()
                        .requestMatchers("/system/**").permitAll()
                        .requestMatchers("/docs","/swagger-ui/**","/v3/**").permitAll()
                        .requestMatchers("/cron/**").permitAll()
                        .requestMatchers(WebSocketProperties.SCOREBOARD_CONNECTION_URL).permitAll()
                        .requestMatchers(WebSocketProperties.CHAT_CONNECTION_URL).permitAll()
                        .requestMatchers("/test/auth").authenticated()
                        .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}