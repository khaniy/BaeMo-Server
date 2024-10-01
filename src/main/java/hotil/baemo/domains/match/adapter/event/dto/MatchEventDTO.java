package hotil.baemo.domains.match.adapter.event.dto;

import lombok.Builder;

import java.util.List;

public interface MatchEventDTO {

    @Builder
    record StatusUpdated(
        Long matchId,
        Integer courtNumber,
        Integer order,
        List<Long> matchUserIds,
        String matchStatus
    ) implements MatchEventDTO {
    }
}
