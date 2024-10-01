package hotil.baemo.domains.community.adapter.input.rest.dto.response;

import lombok.Builder;

import java.util.List;

public interface CommunityResponse {
    @Builder
    record CreateDTO(
            Long communityId
    ) implements CommunityResponse {
    }

    @Builder
    record CategoryListDTO(
            List<String> list
    ) implements CommunityResponse {
    }
}