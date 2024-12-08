package hotil.baemo.domains.clubs.adapter.input.rest.member;


import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.ClubsApi;
import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.JoinClubRequest;
import hotil.baemo.domains.clubs.application.usecases.member.command.ApplyClubUseCase;
import hotil.baemo.domains.clubs.application.usecases.member.command.ApproveMemberUseCase;
import hotil.baemo.domains.clubs.application.usecases.member.command.RejectMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
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
    private final ApplyClubUseCase joinClubUseCase;
    private final ApproveMemberUseCase approveMemberUseCase;
    private final RejectMemberUseCase rejectMemberUseCase;

    @Operation(summary = "모임 신청 API")
    @PostMapping("/{clubsId}")
    public ResponseDTO<Void> getClubsJoin(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        joinClubUseCase.applyClub(new UserId(user.userId()), new ClubId(clubsId));
        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 신청 수락 및 거절 API")
    @PostMapping("/handle")
    public ResponseDTO<Void> getClubsJoinHandle(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid final JoinClubRequest.JoinHandleDTO request
    ) {
        if (request.isAccept()) {
            approveMemberUseCase.approveMember(
                new UserId(user.userId()),
                new ClubId(request.clubsId()),
                new UserId(request.nonMemberId()));
        } else {
            rejectMemberUseCase.rejectMember(
                new UserId(user.userId()),
                new ClubId(request.clubsId()),
                new UserId(request.nonMemberId())
            );
        }

        return ResponseDTO.ok();
    }
}