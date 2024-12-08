package hotil.baemo.domains.exercise.adapter.output.event;


import hotil.baemo.core.event.MatchTopic;
import hotil.baemo.domains.exercise.adapter.output.event.mapper.MatchEventMapper;
import hotil.baemo.domains.exercise.application.ports.output.match.MatchEventOutPort;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class MatchEventProducerAdapter implements MatchEventOutPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void matchStatusUpdated(Match match) {
        if (EnumSet.of(MatchStatus.NEXT, MatchStatus.PROGRESS).contains(match.getMatchStatus())) {
            MatchTopic.StatusUpdatedEvent dto = MatchEventMapper.toStatusUpdated(match);
            eventPublisher.publishEvent(dto);
        }
    }
}
