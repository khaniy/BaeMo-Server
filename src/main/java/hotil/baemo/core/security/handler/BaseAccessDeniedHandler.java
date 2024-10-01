package hotil.baemo.core.security.handler;


import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.util.BaeMoResponseProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BaseAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException) {
        BaeMoResponseProvider
            .setUpHttpServletResponse(response)
            .responseDTO(ResponseDTO.warn(ResponseCode.AUTH_FAILED))
            .status(ResponseCode.AUTH_FAILED)
            .complete();
    }
}