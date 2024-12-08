package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.ClubsApi;
import hotil.baemo.domains.clubs.application.usecases.member.command.ExpelMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
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

    private final ExpelMemberUseCase expelMemberUseCase;

    @Operation(summary = "모임 추방 API")
    @PutMapping("/{clubsId}/kick/{targetId}")
    public ResponseDTO<Void> getKick(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "targetId") final Long targetId
    ) {
        expelMemberUseCase.expelMember(
            new ClubId(clubsId),
            new UserId(user.userId()),
            new UserId(targetId)
        );

        return ResponseDTO.ok();
    }
}