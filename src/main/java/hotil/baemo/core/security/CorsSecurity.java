package hotil.baemo.core.security;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Component
public class CorsSecurity {

    private final List<String> ALLOWED_ORIGIN = List.of(
            "*"
    );
    private final List<String> ALLOWED_HEADER = List.of("*");
    private final List<String> ALLOWED_METHODS = List.of("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH","UPGRADE");
    private final List<String> EXPOSED_HEADER = List.of("Set-Cookie", "Authorization");

    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        ALLOWED_HEADER.forEach(configuration::addAllowedHeader);
        ALLOWED_METHODS.forEach(configuration::addAllowedMethod);
        EXPOSED_HEADER.forEach(configuration::addExposedHeader);

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}