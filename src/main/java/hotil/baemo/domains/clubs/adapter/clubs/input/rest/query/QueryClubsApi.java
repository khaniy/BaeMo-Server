package hotil.baemo.domains.clubs.adapter.clubs.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.*;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ClubsApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class QueryClubsApi {
    private final RetrievePreviewUseCase retrievePreviewUseCase;
    private final RetrieveHomeUseCase retrieveHomeUseCase;
    private final RetrieveUserProfileUseCase retrieveUserProfileUseCase;
    private final RetrieveMemberListUseCase retrieveMemberListUseCase;
    private final RetrieveMyClubsUseCase retrieveMyClubsUseCase;

    @Operation(summary = "Home 모임 리스트 조회")
    @GetMapping("/preview")
    public ResponseDTO<PreviewResponse.ClubsPreviewList> getPreview() {
        final var response = retrievePreviewUseCase.retrieveClubsPreviewList();
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "Home(더보기) 모임 리스트 조회")
    @GetMapping("/home/more")
    public ResponseDTO<PreviewResponse.ClubsPreviewList> getAllPreview(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        final var response = retrievePreviewUseCase.retrieveAllClubsPreviewList(PageRequest.of(page, size));
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 Home 화면 조회 API")
    @GetMapping("/{clubsId}")
    public ResponseDTO<ClubsResponse.HomeDTO> getHome(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        final var response = retrieveHomeUseCase.retrieve(
            new ClubsUserId(user.userId()),
            new ClubsId(clubsId));

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 유저 프로필 조회 API")
    @GetMapping("/profile/user/{userId}")
    public ResponseDTO<PreviewResponse.ClubsPreviewList> getUserProfile(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "userId") final Long userId
    ) {
        final var response = retrieveUserProfileUseCase.retrieve(
            new ClubsUserId(userId));
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 멤버 목록 조회 API")
    @GetMapping("/members/{clubsId}")
    public ResponseDTO<ClubsResponse.MembersDTO> getMemberList(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        final var response = retrieveMemberListUseCase.retrieve(new ClubsId(clubsId), new ClubsUserId(user.userId()));

        return ResponseDTO.ok(response);
    }

    @GetMapping("/my")
    public ResponseDTO<PreviewResponse.ClubsPreviewList> getMyClubsList(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        final var response = retrieveMyClubsUseCase.retrieveMyClubsList(new ClubsUserId(user.userId()));
        return ResponseDTO.ok(response);
    }
}