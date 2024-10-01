package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.FindRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.application.usecases.FindPasswordUseCase;
import hotil.baemo.domains.users.application.usecases.UpdateUsersUseCase;
import hotil.baemo.domains.users.application.usecases.ValidAuthenticationCodeUseCase;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users/phone")
@RequiredArgsConstructor
public class FindPasswordApi {
    private final FindPasswordUseCase findPasswordUseCase;
    private final ValidAuthenticationCodeUseCase validAuthenticationCodeUseCase;
    private final UpdateUsersUseCase updateUsersUseCase;

    @Operation(summary = "비밀번호 찾기를 위한 핸드폰 검증 API")
    @PostMapping("/find/password")
    public ResponseDTO<Void> getValidPhoneForForgotPassword(
        @RequestBody @Valid UsersRequest.ValidPhone request
    ) {
        findPasswordUseCase.validPhone(new Phone(request.phone()));
        return ResponseDTO.ok();
    }

    @Operation(summary = "비밀번호 찾기를 위한 핸드폰 인증 코드 검증 API")
    @PostMapping("/find/password/authentication")
    public ResponseDTO<Void> getValidPhoneForForgotPassword(
        @RequestBody @Valid UsersRequest.ValidPhoneAuthentication request
    ) {
        validAuthenticationCodeUseCase.validForForgotPassword(
            new Phone(request.phone()),
            new AuthenticationCode(request.authenticationCode())
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "핸드폰 인증 이후 비밀번호 업데이트 API")
    @PutMapping("/find/password")
    public ResponseDTO<Void> getUpdatePasswordAfterFind(
        @RequestBody @Valid FindRequest.UpdatePasswordDTO request
    ) {
        updateUsersUseCase.updatePasswordAfterFind(
            new Phone(request.phone()),
            new JoinPassword(request.password())
        );
        return ResponseDTO.ok();
    }
}
