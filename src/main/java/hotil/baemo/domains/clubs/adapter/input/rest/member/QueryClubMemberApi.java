package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.ClubsApi;
import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.application.usecases.member.query.RetrieveClubMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ClubsApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class QueryClubMemberApi {
    private final RetrieveClubMemberUseCase retrieveClubMemberUseCase;


    @Operation(summary = "모임 멤버 목록 조회 API")
    @GetMapping("/members/{clubsId}")
    public ResponseDTO<QClubMemberDTO.Members> getMemberList(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        final var response = retrieveClubMemberUseCase.retrieveClubMembers(new ClubId(clubsId), new UserId(user.userId()));
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 가입 신청한 유저 목록 조회 API")
    @GetMapping("/join/waiting/list/{clubsId}")
    public ResponseDTO<QClubMemberDTO.Members> getNonMemberList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") Long clubsId
    ) {
        final var response = retrieveClubMemberUseCase.retrieveAppliedMembers(new UserId(user.userId()), new ClubId(clubsId));
        return ResponseDTO.ok(response);
    }
}