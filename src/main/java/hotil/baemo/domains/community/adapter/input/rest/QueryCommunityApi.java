package hotil.baemo.domains.community.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.application.usecases.QueryCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@CommunitiesApi
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class QueryCommunityApi {
    private final QueryCommunityUseCase queryCommunityUseCase;

    @Operation(summary = "전체 목록 조회")
    @GetMapping("/list/preview")
    public ResponseDTO<RetrieveCommunity.CommunityPreviewListDTO> getCommunityPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        final var response = queryCommunityUseCase.retrievePreviewList(new CommunityUserId(user.userId()));

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "구독 중인 카테고리의 커뮤니티 목록 조회 API")
    @GetMapping("/subscribe/list/preview")
    public ResponseDTO<RetrieveCommunity.CommunityPreviewListDTO> getSubscribeCommunityPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        final var response = queryCommunityUseCase.retrievePreviewList(new CommunityUserId(user.userId()));

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "특정 타입 카테고리의 커뮤니티 목록 조회 API")
    @GetMapping("/list/preview/{category}")
    public ResponseDTO<RetrieveCommunity.CommunityPreviewListDTO> getCategoryCommunityPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        final var response = queryCommunityUseCase.retrievePreviewList(new CommunityUserId(user.userId()));

        return ResponseDTO.ok(response);
    }

    @Operation(summary = "단일 커뮤니티 조회 API")
    @GetMapping("/details/{communityId}")
    public ResponseDTO<RetrieveCommunity.CommunityDetails> getCommunityDetails(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId
    ) {
        final var response = queryCommunityUseCase.retrieveDetails(CommunityId.of(communityId), new CommunityUserId(user.userId()));

        return ResponseDTO.ok(response);
    }
}