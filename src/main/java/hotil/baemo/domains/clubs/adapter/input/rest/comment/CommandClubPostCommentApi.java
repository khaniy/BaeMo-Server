package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.comment.dto.request.CommentRequestDTO;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.usecases.comment.command.CreateClubPostCommentUseCase;
import hotil.baemo.domains.clubs.application.usecases.comment.command.DeleteClubPostCommentUseCase;
import hotil.baemo.domains.clubs.application.usecases.comment.command.UpdateClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@ClubPostCommentApi
@RequestMapping("/api/clubs/post")
@RequiredArgsConstructor
public class CommandClubPostCommentApi {
    private final CreateClubPostCommentUseCase createClubPostCommentUseCase;
    private final UpdateClubPostCommentUseCase updateClubPostCommentUseCase;
    private final DeleteClubPostCommentUseCase deleteClubPostCommentUseCase;

    @Operation(summary = "모임 게시글의 댓글 작성 API")
    @PostMapping("/{postId}/comment")
    public ResponseDTO<QClubPostCommentDTO.Create> getCreate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long postId,
        @RequestBody final CommentRequestDTO.Create request
    ) {
        final var response = createClubPostCommentUseCase.create(
            new ClubPostId(postId),
            new UserId(user.userId()),
            request.preCommentId() != null ? new ClubPostCommentId(request.preCommentId()) : null,
            new CommentDepth(request.depth()),
            new CommentContent(request.commentContent())
        );

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 게시글의 댓글 수정 API")
    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestBody final CommentRequestDTO.Update request
    ) {
        updateClubPostCommentUseCase.update(
            new ClubPostCommentId(commentId),
            new UserId(user.userId()),
            new CommentContent(request.newCommentContent())
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 게시글의 댓글 삭제 API")
    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseDTO<Void> getDelete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable Long postId,
        @PathVariable Long commentId
    ) {
        deleteClubPostCommentUseCase.delete(
            new ClubPostCommentId(commentId),
            new UserId(user.userId())
        );
        return ResponseDTO.ok();
    }
}