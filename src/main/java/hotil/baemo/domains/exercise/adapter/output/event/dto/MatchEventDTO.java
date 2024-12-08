package hotil.baemo.domains.exercise.adapter.output.event.dto;

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
