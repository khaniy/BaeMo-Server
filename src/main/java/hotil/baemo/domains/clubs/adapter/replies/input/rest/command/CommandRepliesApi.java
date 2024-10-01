package hotil.baemo.domains.clubs.adapter.replies.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.replies.input.rest.annotation.ClubsPostRepliesApi;
import hotil.baemo.domains.clubs.adapter.replies.input.rest.command.dto.request.RepliesRequestDTO;
import hotil.baemo.domains.clubs.application.replies.dto.RepliesUseCaseDTO;
import hotil.baemo.domains.clubs.application.replies.usecases.command.CreateRepliesUseCase;
import hotil.baemo.domains.clubs.application.replies.usecases.command.DeleteRepliesUseCase;
import hotil.baemo.domains.clubs.application.replies.usecases.command.UpdateRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.PreRepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@ClubsPostRepliesApi
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class CommandRepliesApi {
    private final CreateRepliesUseCase createRepliesUseCase;
    private final UpdateRepliesUseCase updateRepliesUseCase;
    private final DeleteRepliesUseCase deleteRepliesUseCase;

    @Operation(summary = "모임 게시글의 댓글 작성 API")
    @PostMapping
    public ResponseDTO<RepliesUseCaseDTO.Create> getCreate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody final RepliesRequestDTO.Create request
    ) {
        final var response = createRepliesUseCase.create(
            new RepliesPostId(request.postId()),
            new RepliesWriter(user.userId()),
            new PreRepliesId(request.preRepliesId()),
            new RepliesContent(request.repliesContent())
        );

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 게시글의 댓글 수정 API")
    @PutMapping
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody final RepliesRequestDTO.Update request
    ) {
        updateRepliesUseCase.update(
            new RepliesId(request.repliesId()),
            new RepliesWriter(user.userId()),
            new RepliesContent(request.newRepliesContent())
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 게시글의 댓글 삭제 API")
    @DeleteMapping("/{repliesId}")
    public ResponseDTO<Void> getDelete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "repliesId") final Long repliesId
    ) {
        deleteRepliesUseCase.delete(
            new RepliesId(repliesId),
            new RepliesWriter(user.userId())
        );
        return ResponseDTO.ok();
    }
}