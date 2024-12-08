package hotil.baemo.domains.exercise.adapter.input.event.score;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.core.event.ScoreBoardTopic;
import hotil.baemo.domains.exercise.adapter.output.event.sse.ScoreBoardSSEAdapter;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreBoardEventConsumerAdapter {

    private final ScoreBoardSSEAdapter sseAdapter;

    @Transactional
    @Async
    @EventListener
    public void exerciseCompleted(ExerciseTopic.CompletedEvent event) {
        sseAdapter.disconnectAllByExerciseId(
            event.exerciseIds().stream().map(ExerciseId::new).collect(Collectors.toList())
        );
    }

    //    @KafkaListener(topics = KafkaProperties.SCOREBOARD_STOPPED_TOPIC)
    @Async
    @EventListener
    public void consumeScoreBoardStopped(ScoreBoardTopic.ScoreStoppedEvent dto) {
        sseAdapter.disconnectAllByMatch(new MatchId(dto.matchId()));
    }

    //    @KafkaListener(topics = KafkaProperties.SCOREBOARD_UPDATED_TOPIC)
    @Async
    @EventListener
    public void consumeScoreBoardUpdated(ScoreBoardTopic.ScoreUpdatedEvent dto) {
        sseAdapter.sendMessage(dto);
    }
}
