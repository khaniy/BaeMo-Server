package hotil.baemo.domains.comment.adapter.input.rest.command;


import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.comment.adapter.input.rest.annotation.CommentApi;
import hotil.baemo.domains.comment.application.usecases.command.CommentLikeUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CommentApi
@RequiredArgsConstructor
public class CommandLikeApi {
    private final CommentLikeUseCase commentLikeUseCase;

    @PostMapping("/api/comment/like/{commentId}")
    public ResponseDTO<Void> getLikeToggle(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "commentId") final Long commentId
    ) {
        commentLikeUseCase.like(
            new CommentWriter(user.userId()),
            new CommentId(commentId)
        );

        return ResponseDTO.ok();
    }
}