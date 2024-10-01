package hotil.baemo.core.security.jwt.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.core.security.jwt.authentication.token.SocialAuthenticationToken;
import hotil.baemo.core.security.jwt.login.dto.JwtLoginDTO;
import hotil.baemo.core.security.jwt.util.JwtUtil;
import hotil.baemo.core.util.BaeMoResponseProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class SocialLoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public SocialLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, String loginUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl(loginUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final var inputStream = request.getInputStream();
            final var requestDTO = OBJECT_MAPPER.readValue(inputStream, JwtLoginDTO.SocialLogin.class);

            final var token = new SocialAuthenticationToken(null, requestDTO.oauthId());

            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new AuthenticationException(ResponseCode.AUTH_FAILED.name(), e) {
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final var customUserDetails = (BaeMoUserDetails) authResult.getPrincipal();
        final var accessToken = getAccessToken(customUserDetails);

        BaeMoResponseProvider.setUpHttpServletResponse(response)
            .header(jwtUtil.getTokenKey(), accessToken)
            .responseDTO(ResponseDTO.ok(
                JwtLoginDTO.Response.builder()
                    .userId(customUserDetails.userId())
                    .name(customUserDetails.name())
                    .profile(customUserDetails.profile())
                    .build()
            ))
            .status(HttpStatus.OK)
            .complete();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaeMoResponseProvider.setUpHttpServletResponse(response)
            .responseDTO(ResponseDTO.warn(ResponseCode.AUTH_FAILED))
            .status(ResponseCode.AUTH_FAILED.getHttpStatus())
            .complete();
    }

    private String getAccessToken(BaeMoUserDetails customUserDetails) {
        final var userName = customUserDetails.userId();

        final var userAuthorities = customUserDetails.getAuthorities()
            .iterator()
            .next()
            .getAuthority();

        return jwtUtil.generateAccessToken(userName, userAuthorities);
    }
}