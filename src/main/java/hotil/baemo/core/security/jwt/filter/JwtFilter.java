package hotil.baemo.core.security.jwt.filter;


import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetailsService;
import hotil.baemo.core.security.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final BaeMoUserDetailsService baeMoUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain) throws ServletException, IOException {
        final var header = request.getHeader(jwtUtil.getTokenKey());
        final var token = jwtUtil.parseToken(header);

        if (jwtUtil.isValidToken(token)) {
            final var userIdx = jwtUtil.getUserIdx(token);

            final var baeMoUsersEntity = baeMoUserDetailsService.loadUserByUserId(userIdx);

            final var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(baeMoUsersEntity, null, baeMoUsersEntity.getAuthorities());

            saveAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private static void saveAuthentication(UsernamePasswordAuthenticationToken token) {
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
    }
}