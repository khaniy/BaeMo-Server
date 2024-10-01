package hotil.baemo.domains.clubs.adapter.clubs.input.rest.operation;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.application.clubs.usecases.operation.KickUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsApi
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class KickClubsApi {

    private final KickUseCase kickUseCase;

    @Operation(summary = "모임 추방 API")
    @PutMapping("/{clubsId}/kick/{targetId}")
    public ResponseDTO<Void> getKick(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "targetId") final Long targetId
    ) {
        kickUseCase.kick(
            new ClubsId(clubsId),
            new ClubsUserId(user.userId()),
            new ClubsUserId(targetId)
        );

        return ResponseDTO.ok();
    }
}