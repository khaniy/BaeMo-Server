package hotil.baemo.domains.clubs.adapter.clubs.input.rest.command;


import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request.JoinClubRequest;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.JoinClubsUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs/join")
public class JoinClubsApi {
    private final JoinClubsUseCase joinClubUseCase;

    @Operation(summary = "모임 신청 API")
    @PostMapping("/{clubsId}")
    public ResponseDTO<Void> getClubsJoin(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        joinClubUseCase.join(new ClubsUserId(user.userId()), new ClubsId(clubsId));
        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 신청 수락 및 거절 API")
    @PostMapping("/handle")
    public ResponseDTO<Void> getClubsJoinHandle(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid final JoinClubRequest.JoinHandleDTO request
    ) {
        joinClubUseCase.handle(
            new ClubsUserId(user.userId()),
            new ClubsId(request.clubsId()),
            new ClubsNonMember(new ClubsUserId(request.nonMemberId()), new ClubsId(request.clubsId())),
            request.isAccept()
        );

        return ResponseDTO.ok();
    }
}