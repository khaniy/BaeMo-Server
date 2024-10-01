package hotil.baemo.domains.clubs.adapter.clubs.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveJoinUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs/join/waiting/list")
public class NonMemberQueryApi {
    private final RetrieveJoinUseCase retrieveJoinUseCase;

    @Operation(summary = "모임 가입 신청한 유저 목록 조회 API")
    @GetMapping("/{clubsId}")
    public ResponseDTO<JoinResponse.GetDTOList> getNonMemberList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") Long clubsId
    ) {
        final var nonMembers = retrieveJoinUseCase.retrieveList(new ClubsUserId(user.userId()), new ClubsId(clubsId));
        return ResponseDTO.ok(nonMembers);
    }
}