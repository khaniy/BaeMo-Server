package hotil.baemo.domains.comment.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.comment.adapter.input.rest.annotation.CommentApi;
import hotil.baemo.domains.comment.adapter.input.rest.dto.request.CommentRequest;
import hotil.baemo.domains.comment.adapter.input.rest.dto.response.CommentResponse;
import hotil.baemo.domains.comment.application.usecases.command.CommandCommentUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CommentApi
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommandCommentApi {
    private final CommandCommentUseCase commandCommentUseCase;

    @Operation(summary = "댓글 생성 API")
    @PostMapping()
    public ResponseDTO<CommentResponse.CreateDTO> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody final CommentRequest.CreateDTO request
    ) {
        final var commentId = commandCommentUseCase.create(
            request.toCommentCommunityId(),
            request.toPreCommentId(),
            request.toCommentContent(),
            new CommentWriter(user.userId()));

        return ResponseDTO.ok(CommentResponse.CreateDTO.builder()
            .commentId(commentId.id())
            .build());
    }

    @Operation(summary = "댓글 수정 API")
    @PutMapping("/{commentId}")
    public ResponseDTO<Void> getPut(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "commentId") final Long commentId,
        @RequestBody final CommentRequest.UpdateDTO request
    ) {
        commandCommentUseCase.update(
            new CommentId(commentId),
            request.toCommentContent(),
            new CommentWriter(user.userId()));

        return ResponseDTO.ok();
    }

    @Operation(summary = "댓글 삭제 API")
    @DeleteMapping("/{commentId}")
    public ResponseDTO<Void> getDelete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "commentId") final Long commentId
    ) {
        commandCommentUseCase.delete(
            new CommentId(commentId),
            new CommentWriter(user.userId()));

        return ResponseDTO.ok();
    }
}