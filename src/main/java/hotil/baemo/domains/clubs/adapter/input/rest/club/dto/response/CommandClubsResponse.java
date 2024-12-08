package hotil.baemo.domains.clubs.adapter.input.rest.club.dto.response;

import lombok.Builder;

public interface CommandClubsResponse {
    @Builder
    record CreateDTO(
        Long clubsId
    ) implements CommandClubsResponse {
    }
}
