package hotil.baemo.domains.clubs.adapter.post.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.post.input.dto.response.ClubsPostResponse;
import hotil.baemo.domains.clubs.adapter.post.input.rest.annotation.ClubsPostApi;
import hotil.baemo.domains.clubs.application.post.usecases.command.LikeClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsPostApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class LikeClubsPostApi {
    private final LikeClubsPostUseCase likeClubsPostUseCase;

    @Operation(summary = "모임 게시글 좋아요 API")
    @PostMapping("/{clubsId}/post/{postId}/like")
    public ResponseDTO<ClubsPostResponse.LikeResult> getLikeToggle(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @PathVariable(name = "postId") final Long postId
    ) {
        final var result = likeClubsPostUseCase.likeToggle(
            new ClubsPostId(postId),
            new ClubsUserId(user.userId()),
            new ClubsId(clubsId)
        );

        return ResponseDTO.ok(ClubsPostResponse.LikeResult.builder()
            .isLike(result.isLike())
            .build()
        );
    }
}