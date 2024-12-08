package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.ClubsApi;
import hotil.baemo.domains.clubs.application.usecases.member.command.ExitClubUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
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
    private final ExitClubUseCase exitClubUseCase;

    @Operation(summary = "모임 탈퇴 API")
    @DeleteMapping("/exit/{clubsId}")
    public ResponseDTO<Void> getExitClubs(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        exitClubUseCase.exitClub(
            new ClubId(clubsId),
            new UserId(user.userId())
        );

        return ResponseDTO.ok();
    }
}