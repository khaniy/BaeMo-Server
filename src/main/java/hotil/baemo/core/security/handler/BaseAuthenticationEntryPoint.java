package hotil.baemo.core.security.handler;


import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.util.BaeMoResponseProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BaseAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) {
        BaeMoResponseProvider
            .setUpHttpServletResponse(response)
            .responseDTO(ResponseDTO.warn(ResponseCode.AUTH_RESTRICTED))
            .status(ResponseCode.AUTH_RESTRICTED)
            .complete();
    }
}