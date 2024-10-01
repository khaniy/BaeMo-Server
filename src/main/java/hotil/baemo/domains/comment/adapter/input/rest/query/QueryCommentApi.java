package hotil.baemo.domains.comment.adapter.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.comment.adapter.input.rest.annotation.CommentApi;
import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.application.usecases.QueryCommentUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@CommentApi
@RequiredArgsConstructor
public class QueryCommentApi {
    private final QueryCommentUseCase queryCommentUseCase;

    @Operation(summary = "단일 커뮤니티의 댓글 목록 조회 API")
    @GetMapping("/api/comment/{communityId}")
    public ResponseDTO<RetrieveComment.CommentDetailsList> getList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId,
        @PageableDefault(page = 0, size = Integer.MAX_VALUE, sort = "createdAt", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        final var response = queryCommentUseCase.retrieveCommentListByCommunity(
            new CommentCommunityId(communityId),
            pageable
        );
        return ResponseDTO.ok(response);
    }
}