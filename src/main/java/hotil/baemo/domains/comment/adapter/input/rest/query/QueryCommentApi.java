package hotil.baemo.domains.comment.adapter.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.comment.adapter.input.rest.annotation.CommentApi;
import hotil.baemo.domains.comment.adapter.input.rest.dto.response.CommentResponse;
import hotil.baemo.domains.comment.application.usecases.QueryCommentUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CommentApi
@RequiredArgsConstructor
public class QueryCommentApi {
    private final QueryCommentUseCase queryCommentUseCase;

    @Operation(summary = "단일 커뮤니티의 댓글 목록 조회 API")
    @GetMapping("/api/comment/{communityId}")
    public ResponseDTO<CommentResponse.CommentDetailsList> getList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId,
        @PageableDefault(page = 0, size = 30, sort = "createdAt", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        final var result = queryCommentUseCase.retrieveCommentListByCommunity(
            new CommentCommunityId(communityId),
            new CommunityUserId(user.userId()),
            pageable
        );

        return ResponseDTO.ok(
            CommentResponse
                .CommentDetailsList
                .convert(result)
        );
    }
}