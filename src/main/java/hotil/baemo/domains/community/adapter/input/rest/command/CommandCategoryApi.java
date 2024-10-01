package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.adapter.input.rest.dto.request.CategoryRequest;
import hotil.baemo.domains.community.application.usecases.CategoryUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CommunitiesApi
@RequestMapping("/api/communities/category")
@RequiredArgsConstructor
public class CommandCategoryApi {
    private final CategoryUseCase categoryUseCase;

    @Operation(summary = "카테고리 구독 API")
    @PostMapping()
    public ResponseDTO<Void> getSubscribe(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody final CategoryRequest.SubscribeDTO request
    ) {
        categoryUseCase.subscribe(
            request.toCategoryList(),
            new CommunityUserId(user.userId()));
        return ResponseDTO.ok();
    }
}