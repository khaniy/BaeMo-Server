package hotil.baemo.core.security.jwt.filter;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.util.BaeMoResponseProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final ResponseDTO<Void> responseDTO = ResponseDTO.ok();

        BaeMoResponseProvider.setUpHttpServletResponse(response)
                .responseDTO(responseDTO)
                .status(HttpStatus.OK)
                .complete();
    }
}
