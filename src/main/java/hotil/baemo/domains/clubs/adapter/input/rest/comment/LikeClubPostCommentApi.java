package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.application.usecases.comment.command.LikeClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubPostCommentApi
@RequestMapping("/api/clubs/post")
@RequiredArgsConstructor
public class LikeClubPostCommentApi {
    private final LikeClubPostCommentUseCase likeClubPostCommentUseCase;

    @Operation(summary = "모임 게시글의 댓글 좋아요 토글 API")
    @PatchMapping("/{postId}/comment/{commentId}/like")
    public ResponseDTO<Void> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable final Long postId,
        @PathVariable final Long commentId
    ) {
        likeClubPostCommentUseCase.like(
            new ClubPostCommentId(commentId),
            new UserId(user.userId())
        );

        return ResponseDTO.ok();
    }
}