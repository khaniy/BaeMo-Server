package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.usecases.comment.query.RetrieveClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubPostCommentApi
@RequestMapping("/api/clubs/post")
@RequiredArgsConstructor
public class QueryClubPostCommentApi {
    private final RetrieveClubPostCommentUseCase retrieveClubPostCommentUseCase;

    @Operation(summary = "모임 게시글의 댓글 조회")
    @GetMapping("/{postId}/comment")
    public ResponseDTO<QClubPostCommentDTO.CommentDetailList> getCreate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable("postId") final Long postId
    ) {
        final var response = retrieveClubPostCommentUseCase.retrieve(new UserId(user.userId()), new ClubPostId(postId));

        return ResponseDTO.ok(response);
    }
}