package hotil.baemo.domains.community.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityResponse;
import hotil.baemo.domains.community.application.usecases.CategoryUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CommunitiesApi
@RequestMapping("/api/communities/category")
@RequiredArgsConstructor
public class QueryCategoryApi {
    private final CategoryUseCase categoryUseCase;

    @Operation(summary = "모든 카테고리 조회 API")
    @GetMapping()
    public ResponseDTO<CommunityResponse.CategoryListDTO> getList() {
        return ResponseDTO.ok(CommunityResponse.CategoryListDTO.builder()
            .list(CommunityCategory.getAllList())
            .build());
    }

    @Operation(summary = "구독중인 카테고리 조회 API")
    @GetMapping("/subscribed")
    public ResponseDTO<CommunityResponse.CategoryListDTO> getSubscribedList(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        final var subscribedList = categoryUseCase.getSubscribedList(new CommunityUserId(user.userId()));

        return ResponseDTO.ok(CommunityResponse.CategoryListDTO.builder()
            .list(subscribedList.stream()
                .map(CommunityCategory::getDescription)
                .toList())
            .build());
    }
}