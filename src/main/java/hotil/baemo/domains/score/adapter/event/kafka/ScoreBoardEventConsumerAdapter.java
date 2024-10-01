package hotil.baemo.domains.score.adapter.event.kafka;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.score.adapter.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.score.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.score.adapter.event.sse.ScoreBoardSSEAdapter;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreBoardEventConsumerAdapter {

    private final ScoreBoardSSEAdapter sseAdapter;

    @Transactional
    @KafkaListener(topics = KafkaProperties.EXERCISE_COMPLETED,
        groupId = KafkaProperties.MATCH_STATIC_GROUP_ID)
    public void exerciseCompleted(String message) {
        ExerciseEventDTO.Completed dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.Completed.class);
        sseAdapter.disconnectAllByExerciseId(
            dto.exerciseIds().stream().map(ExerciseId::new).collect(Collectors.toList())
        );
    }

    @KafkaListener(topics = KafkaProperties.SCOREBOARD_STOPPED_TOPIC)
    public void consumeScoreBoardStopped(EventScoreBoardDTO.Updated dto) throws IOException {
        sseAdapter.disconnectAllByMatch(new MatchId(dto.matchId()));
    }

    @KafkaListener(topics = KafkaProperties.SCOREBOARD_UPDATED_TOPIC)
    public void consumeScoreBoardUpdated(EventScoreBoardDTO.Updated dto) throws IOException {
        sseAdapter.sendMessage(dto);
    }
}
