package hotil.baemo.domains.clubs.adapter.clubs.input.rest.operation;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.application.clubs.usecases.operation.ExitUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsApi
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ExitClubsApi {
    private final ExitUseCase exitUseCase;

    @Operation(summary = "모임 탈퇴 API")
    @DeleteMapping("/exit/{clubsId}")
    public ResponseDTO<Void> getExitClubs(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        exitUseCase.exitClubs(
            new ClubsId(clubsId),
            new ClubsUserId(user.userId())
        );

        return ResponseDTO.ok();
    }
}