package hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.response;

import lombok.Builder;

public interface CommandClubsResponse {
    @Builder
    record CreateDTO(
        Long clubsId
    ) implements CommandClubsResponse {
    }
}
