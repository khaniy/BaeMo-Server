package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.response.ValidPhoneResponse;
import hotil.baemo.domains.users.application.usecases.ValidUserUseCase;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users/phone")
@RequiredArgsConstructor
public class ValidUserApi {
    private final ValidUserUseCase validUserUseCase;

    @Operation(summary = "회원 가입을 위한 핸드폰 검증 API")
    @PostMapping("/sign-up")
    public ResponseDTO<Void> getValidPhoneForSignUp(
        @RequestBody @Valid final UsersRequest.ValidPhone request
    ) {
        validUserUseCase.validPhoneForSignUp(new Phone(request.phone()));
        return ResponseDTO.ok();
    }

    @Operation(summary = "회원 가입을 위한 핸드폰 인증 코드 검증 API")
    @PostMapping("/authentication")
    public ResponseDTO<ValidPhoneResponse.Result> getValidPhone(
        @RequestBody @Valid final UsersRequest.ValidPhoneAuthentication request
    ) {
        final var response = validUserUseCase.validAuthenticationCodeForSignUp(
            new Phone(request.phone()),
            new AuthenticationCode(request.authenticationCode())
        );
        return ResponseDTO.ok(
            ValidPhoneResponse.Result.builder()
                .name(response.realName() != null ? response.realName().name() : null)
                .type(response.type())
                .build()
        );
    }

    @Operation(summary = "비밀번호 찾기를 위한 핸드폰 검증 API")
    @PostMapping("/find/password")
    public ResponseDTO<Void> getValidPhoneForForgotPassword(
        @RequestBody @Valid UsersRequest.ValidPhone request
    ) {
        validUserUseCase.validPhoneForForgotPassword(new Phone(request.phone()));
        return ResponseDTO.ok();
    }

    @Operation(summary = "비밀번호 찾기를 위한 핸드폰 인증 코드 검증 API")
    @PostMapping("/find/password/authentication")
    public ResponseDTO<Void> getValidPhoneForForgotPassword(
        @RequestBody @Valid UsersRequest.ValidPhoneAuthentication request
    ) {
        validUserUseCase.validAuthenticationCodeForForgotPassword(
            new Phone(request.phone()),
            new AuthenticationCode(request.authenticationCode())
        );

        return ResponseDTO.ok();
    }
}