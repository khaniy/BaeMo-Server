package hotil.baemo.core.event;

import lombok.Builder;

import java.util.List;

public interface MatchTopic {

    @Builder
    record StatusUpdatedEvent(
        Long matchId,
        Long exerciseId,
        Integer courtNumber,
        Integer order,
        List<Long> matchUserIds,
        String matchStatus
    ) implements MatchTopic {
    }
}
