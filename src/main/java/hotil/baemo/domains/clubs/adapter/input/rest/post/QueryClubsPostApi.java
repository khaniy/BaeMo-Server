package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.application.usecases.post.query.RetrieveClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ClubsPostApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class QueryClubsPostApi {
    private final RetrieveClubPostUseCase retrieveClubPostUseCase;

    @Operation(summary = "모임의 게시판 화면. 게시글 전체 조회 API")
    @GetMapping("/{clubsId}/post")
    public ResponseDTO<QClubPostDTO.ClubPostMain> getPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @RequestParam(name = "page", defaultValue = "0") final int page,
        @RequestParam(name = "size", defaultValue = "10") final int size
    ) {
        final var response = retrieveClubPostUseCase.retrievePreview(
            new UserId(user.userId()),
            new ClubId(clubsId),
            PageRequest.of(page, size)
        );
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임의 게시판 화면. 타입별 게시글 전체 조회 API")
    @GetMapping("/{clubsId}/post/type/{type}")
    public ResponseDTO<QClubPostDTO.ClubPostFiltered> getFilteredPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "type") final ClubPostType type,
        @RequestParam(name = "page", defaultValue = "0") final int page,
        @RequestParam(name = "size", defaultValue = "10") final int size
    ) {
        final var response = retrieveClubPostUseCase.retrievePreview(
            new UserId(user.userId()),
            new ClubId(clubsId),
            type,
            PageRequest.of(page, size)
        );
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임의 게시글 상세 조회 API")
    @GetMapping("/{clubsId}/post/{postId}")
    public ResponseDTO<QClubPostDTO.ClubPostDetailView> getDetails(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "postId") final Long postId
    ) {
        final var response = retrieveClubPostUseCase.retrievePost(
            new ClubPostId(postId),
            new ClubId(clubsId),
            new UserId(user.userId()));
        return ResponseDTO.ok(response);
    }
}