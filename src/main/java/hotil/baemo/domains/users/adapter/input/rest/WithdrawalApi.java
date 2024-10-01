package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.application.usecases.WithdrawalUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class WithdrawalApi {

    private final WithdrawalUseCase withdrawalUseCase;

    @Operation(summary = "유저 회원 탈퇴 API")
    @DeleteMapping
    public ResponseDTO<Void> getWithdrawal(
        @AuthenticationPrincipal BaeMoUserDetails users
    ) {
        withdrawalUseCase.withdrawal(new UsersId(users.userId()));

        return ResponseDTO.ok();
    }
}