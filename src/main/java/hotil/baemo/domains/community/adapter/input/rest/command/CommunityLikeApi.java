package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.application.usecases.command.LikeCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CommunitiesApi
@RequiredArgsConstructor
public class CommunityLikeApi {
    private final LikeCommunityUseCase likeCommunityUseCase;

    @Operation(summary = "커뮤니티 좋아요 Api")
    @PostMapping("/api/communities/like/{communityId}")
    public ResponseDTO<Void> getLikeCommunity(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId
    ) {
        likeCommunityUseCase.like(
            new CommunityId(communityId),
            new CommunityUserId(user.userId())
        );

        return ResponseDTO.ok();
    }
}