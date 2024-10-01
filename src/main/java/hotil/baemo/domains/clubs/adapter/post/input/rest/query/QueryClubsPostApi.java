package hotil.baemo.domains.clubs.adapter.post.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.post.input.rest.annotation.ClubsPostApi;
import hotil.baemo.domains.clubs.application.post.usecases.query.RetrieveClubsPostUseCase;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
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
    private final RetrieveClubsPostUseCase retrieveClubsPostUseCase;

    @Operation(summary = "모임의 게시판 화면. 게시글 전체 조회 API")
    @GetMapping("/{clubsId}/post")
    public ResponseDTO<RetrieveClubsPostDTO.PreviewDTO> getPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @RequestParam(name = "page", defaultValue = "0") final int page,
        @RequestParam(name = "size", defaultValue = "10") final int size
    ) {
        final var response = retrieveClubsPostUseCase.retrievePreview(
            new ClubsUserId(user.userId()),
            new ClubsId(clubsId),
            PageRequest.of(page, size)
        );
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임의 게시판 화면. 타입별 게시글 전체 조회 API")
    @GetMapping("/{clubsId}/post/type/{type}")
    public ResponseDTO<RetrieveClubsPostDTO.TypePreviewDTO> getFilteredPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "type") final ClubsPostType type,
        @RequestParam(name = "page", defaultValue = "0") final int page,
        @RequestParam(name = "size", defaultValue = "10") final int size
    ) {
        final var response = retrieveClubsPostUseCase.retrievePreview(
            new ClubsUserId(user.userId()),
            new ClubsId(clubsId),
            type,
            PageRequest.of(page, size)
        );
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임의 게시글 상세 조회 API")
    @GetMapping("/{clubsId}/post/{postId}")
    public ResponseDTO<DetailsClubsPostDTO.Details> getDetails(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "postId") final Long postId
    ) {
        final var response = retrieveClubsPostUseCase.retrievePost(
            new ClubsPostId(postId),
            new ClubsId(clubsId),
            new ClubsUserId(user.userId()));
        return ResponseDTO.ok(response);
    }
}