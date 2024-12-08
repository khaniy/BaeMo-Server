package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.request.ClubsPostRequest;
import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.response.ClubsPostResponse;
import hotil.baemo.domains.clubs.application.usecases.post.command.CreateClubPostUseCase;
import hotil.baemo.domains.clubs.application.usecases.post.command.DeleteClubPostUseCase;
import hotil.baemo.domains.clubs.application.usecases.post.command.LikeClubPostUseCase;
import hotil.baemo.domains.clubs.application.usecases.post.command.UpdateClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@ClubsPostApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class CommandClubsPostApi {
    private final CreateClubPostUseCase createClubPostUseCase;
    private final UpdateClubPostUseCase updateClubPostUseCase;
    private final LikeClubPostUseCase likeClubPostUseCase;
    private final DeleteClubPostUseCase deleteClubPostUseCase;

    @Operation(summary = "모임 게시글 작성 API")
    @PostMapping("/post")
    public ResponseDTO<ClubsPostResponse.CreateDTO> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final ClubsPostRequest.CreateDTO createPostDTO
    ) {
        final var clubsPostId = createClubPostUseCase.create(
            createPostDTO.toClubId(),
            new UserId(user.userId()),
            createPostDTO.toVOGroup()
        );

        return ResponseDTO.ok(ClubsPostResponse.CreateDTO.builder()
            .clubsPostId(clubsPostId.id())
            .build());
    }

    @Operation(summary = "모임 게시글 수정 API")
    @PutMapping("/post")
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final ClubsPostRequest.UpdateDTO request
    ) {
        updateClubPostUseCase.update(
            request.toClubsPostId(),
            new UserId(user.userId()),
            request.toVOGroup()
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 게시글 좋아요 API")
    @PostMapping("/{clubsId}/post/{postId}/like")
    public ResponseDTO<ClubsPostResponse.LikeResult> getClubsPostLikeToggle(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "postId") final Long postId
    ) {
        final var result = likeClubPostUseCase.likeToggle(
            new ClubPostId(postId),
            new UserId(user.userId()),
            new ClubId(clubsId)
        );

        return ResponseDTO.ok(ClubsPostResponse.LikeResult.builder()
            .isLike(result.isLike())
            .build()
        );
    }

    @Operation(summary = "모임 게시글 삭제 API")
    @DeleteMapping("/post/{clubsPostId}/clubsId/{clubsId}")
    public ResponseDTO<Void> delete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsPostId") final Long clubsPostId,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        deleteClubPostUseCase.delete(
            new UserId(user.userId()),
            new ClubPostId(clubsPostId),
            new ClubId(clubsId)
        );

        return ResponseDTO.ok();
    }
}