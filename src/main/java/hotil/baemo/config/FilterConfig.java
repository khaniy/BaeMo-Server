package hotil.baemo.config;

import hotil.baemo.core.security.filter.CommunityFilter;
import hotil.baemo.core.security.filter.FinalSecurityFilter;
import hotil.baemo.core.security.filter.UsersFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FilterConfig {
    private final UsersFilter userFilter;
    private final CommunityFilter communityFilter;
    private final FinalSecurityFilter finalSecurityFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain communityFilterChain(HttpSecurity http) throws Exception {
        return communityFilter.doFilterChain(http);
    }

    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        return userFilter.doFilterChain(http);
    }

    @Bean
    @Order
    public SecurityFilterChain finalSecurityFilterChain(HttpSecurity http) throws Exception {
        return finalSecurityFilter.doFilterChain(http);
    }
}