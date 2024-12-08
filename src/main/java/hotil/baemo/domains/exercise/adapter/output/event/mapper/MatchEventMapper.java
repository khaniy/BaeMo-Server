package hotil.baemo.domains.exercise.adapter.output.event.mapper;

import hotil.baemo.core.event.MatchTopic;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public class MatchEventMapper {

    public static MatchTopic.StatusUpdatedEvent toStatusUpdated(Match match) {
        return MatchTopic.StatusUpdatedEvent.builder()
            .matchId(match.getMatchId().id())
            .exerciseId(match.getExerciseId().id())
            .courtNumber(match.getCourtNumber() != null ? match.getCourtNumber().number() : null)
            .order(match.getOrder().order())
            .matchUserIds(match.getUserIds().stream().map(UserId::id).toList())
            .matchStatus(match.getMatchStatus().toString())
            .build();
    }
}