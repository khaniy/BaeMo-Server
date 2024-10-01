package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.adapter.input.rest.dto.request.CommunityRequest;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityResponse;
import hotil.baemo.domains.community.application.usecases.command.CreateCommunityUseCase;
import hotil.baemo.domains.community.application.usecases.command.DeleteCommunityUseCase;
import hotil.baemo.domains.community.application.usecases.command.UpdateCommunityUseCase;
import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CommunitiesApi
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommandCommunityApi {
    private final CreateCommunityUseCase createCommunityUseCase;
    private final UpdateCommunityUseCase updateCommunityUseCase;
    private final DeleteCommunityUseCase deleteCommunityUseCase;

    @Operation(summary = "커뮤니티 생성 API")
    @PostMapping()
    public ResponseDTO<CommunityResponse.CreateDTO> getCreateCommunity(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody final CommunityRequest.CreateDTO request
    ) {

        final var response = createCommunityUseCase.create(CommunityCreateAggregate.builder()
            .communityWriter(new CommunityWriter(user.userId()))
            .communityTitle(request.toCommunityTitle())
            .communityCategory(request.category())
            .communityContent(request.toCommunityContent())
            .communityImageList(request.toCommunityImageList())
            .build());

        return ResponseDTO.ok(CommunityResponse.CreateDTO.builder()
            .communityId(response.id())
            .build());
    }

    @Operation(summary = "커뮤니티 수정 API")
    @PutMapping()
    public ResponseDTO<Void> getUpdateCommunity(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody final CommunityRequest.UpdateDTO request
    ) {

        updateCommunityUseCase.update(CommunityUpdateAggregate.builder()
            .communityWriter(new CommunityWriter(user.userId()))
            .communityId(request.toCommunityId())
            .communityTitle(request.toCommunityTitle())
            .communityContent(request.toCommunityContent())
            .communityCategory(request.category())
            .communityImageList(request.toCommunityImageList())
            .build());

        return ResponseDTO.ok();
    }

    @Operation(summary = "커뮤니티 삭제 API")
    @DeleteMapping("/{communityId}")
    public ResponseDTO<Void> getDeleteCommunity(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "communityId") final Long communityId
    ) {
        deleteCommunityUseCase.delete(CommunityId.of(communityId), new CommunityWriter(user.userId()));
        return ResponseDTO.ok();
    }
}