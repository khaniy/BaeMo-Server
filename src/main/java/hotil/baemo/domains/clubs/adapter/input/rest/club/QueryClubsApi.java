package hotil.baemo.domains.clubs.adapter.input.rest.club;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.application.usecases.club.query.RetrieveClubDetailUseCase;
import hotil.baemo.domains.clubs.application.usecases.club.query.RetrieveClubListUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ClubsApi
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class QueryClubsApi {

    private final RetrieveClubListUseCase retrieveClubListUseCase;
    private final RetrieveClubDetailUseCase retrieveClubDetailUseCase;

    @Operation(summary = "Home 모임 리스트 조회")
    @GetMapping("/preview")
    public ResponseDTO<QClubDTO.ClubPreviewList> getPreview() {
        final var response = retrieveClubListUseCase.retrieveHomePreviewList();
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "Home(더보기) 모임 리스트 조회")
    @GetMapping("/home/more")
    public ResponseDTO<QClubDTO.ClubPreviewList> getAllPreview(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        final var response = retrieveClubListUseCase.retrieveAllClubsPreviewList(PageRequest.of(page, size));
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 상세 화면 조회 API")
    @GetMapping("/{clubsId}")
    public ResponseDTO<QClubDTO.ClubDetailView> getHome(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        final var response = retrieveClubDetailUseCase.retrieveClubDetail(
            new UserId(user.userId()),
            new ClubId(clubsId));

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "내 모임 조회 API")
    @GetMapping("/my")
    public ResponseDTO<QClubDTO.ClubPreviewList> getMyClubsList(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        final var response = retrieveClubListUseCase.retrieveUserClubsList(new UserId(user.userId()));
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "유저 프로필 조회시 참여한 모임 조회")
    @GetMapping("/profile/user/{userId}")
    public ResponseDTO<QClubDTO.ClubPreviewList> getUserClubList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "userId") Long userId
    ) {
        final var response = retrieveClubListUseCase.retrieveUserClubsList(new UserId(userId));
        return ResponseDTO.ok(response);
    }
}