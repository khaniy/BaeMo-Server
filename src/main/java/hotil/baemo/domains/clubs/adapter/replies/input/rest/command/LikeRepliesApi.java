package hotil.baemo.domains.clubs.adapter.replies.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.replies.input.rest.annotation.ClubsPostRepliesApi;
import hotil.baemo.domains.clubs.application.replies.usecases.command.LikeRepliesUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsPostRepliesApi
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class LikeRepliesApi {
    private final LikeRepliesUseCase likeRepliesUseCase;

    @Operation(summary = "모임 게시글의 댓글 좋아요 토글 API")
    @PostMapping("/like/{repliesId}")
    public ResponseDTO<Void> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "repliesId") final Long repliesId
    ) {
        likeRepliesUseCase.like(
            new RepliesId(repliesId),
            new ClubsUserId(user.userId())
        );

        return ResponseDTO.ok();
    }
}