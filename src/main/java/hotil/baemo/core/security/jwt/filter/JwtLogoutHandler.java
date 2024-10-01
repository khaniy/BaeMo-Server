package hotil.baemo.core.security.jwt.filter;

import hotil.baemo.core.cookie.CookieUtil;
import hotil.baemo.core.security.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String token = CookieUtil.findCookieByKey(request, jwtUtil.getTokenKey());

        if (jwtUtil.isValidToken(token)) {
            final String parsedToken = jwtUtil.parseToken(token);
            jwtUtil.removeToken(parsedToken);
            CookieUtil.removeCookieByKey(response, jwtUtil.getTokenKey());
        }
    }
}