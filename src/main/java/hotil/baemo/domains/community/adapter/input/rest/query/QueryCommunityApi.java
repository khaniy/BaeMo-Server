package hotil.baemo.domains.community.adapter.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.QueryResponse;
import hotil.baemo.domains.community.application.usecases.query.QueryCommunityUseCase;
import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CommunitiesApi
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class QueryCommunityApi {
    private final QueryCommunityUseCase queryCommunityUseCase;

    @Operation(summary = "미리보기 목록 조회 API")
    @GetMapping("/list/preview")
    public ResponseDTO<QueryResponse.PreviewList> getCommunityPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable,
        @RequestParam(name = "category", required = false) final List<String> category
    ) {
        final var result = queryCommunityUseCase.read(
            new CommunityUserId(user.userId()),
            pageable,
            category != null ? CategoryList.ofName(category) : null
        );

        return ResponseDTO.ok(
            QueryResponse.PreviewList.of(result)
        );
    }

    @Operation(summary = "구독 중인 카테고리의 커뮤니티 목록 조회 API")
    @GetMapping("/subscribe/list/preview")
    public ResponseDTO<QueryResponse.PreviewList> getSubscribeCommunityPreviewList(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        final var result = queryCommunityUseCase.readSubscribe(
            new CommunityUserId(user.userId()),
            pageable
        );

        return ResponseDTO.ok(
            QueryResponse.PreviewList.of(result)
        );
    }

    @Operation(summary = "커뮤니티 상세보기 API")
    @GetMapping("/details/{communityId}")
    public ResponseDTO<RetrieveCommunity.ReadCommunityDetails> getCommunityDetails(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId
    ) {
        final var result = queryCommunityUseCase.readDetails(
            new CommunityId(communityId),
            new CommunityUserId(user.userId())
        );

        return ResponseDTO.ok(result);
    }
}