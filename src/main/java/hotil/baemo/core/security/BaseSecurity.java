package hotil.baemo.core.security;


import hotil.baemo.core.security.handler.BaseAccessDeniedHandler;
import hotil.baemo.core.security.handler.BaseAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
@RequiredArgsConstructor
public class BaseSecurity extends AbstractHttpConfigurer<BaseSecurity, HttpSecurity> {
    private final CorsSecurity corsSecurity;

    private final BaseAccessDeniedHandler baseAccessDeniedHandler;
    private final BaseAuthenticationEntryPoint baseAuthenticationEntryPoint;
    private boolean flag;

    @Override
    public void init(HttpSecurity http) throws Exception {
        if (flag) {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(cors -> cors.configurationSource(corsSecurity.corsConfigurationSource()))
                .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
                    .accessDeniedHandler(baseAccessDeniedHandler)
                    .authenticationEntryPoint(baseAuthenticationEntryPoint))
            ;
        }
    }

    public void active() {
        this.flag = true;
    }
}