package hotil.baemo.domains.exercise.adapter.output.event;

import hotil.baemo.domains.exercise.adapter.input.event.dto.ScoreBoardEventMapper;
import hotil.baemo.domains.exercise.application.ports.output.score.ScoreBoardEventOutPort;
import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreBoardEventProducerAdapter implements ScoreBoardEventOutPort {

    //    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void scoreBoardUpdated(ScoreBoard scoreBoard) {

        final var dto = ScoreBoardEventMapper.toUpdated(scoreBoard);
        eventPublisher.publishEvent(dto);
//        kafkaTemplate.send(MessageBuilder.withPayload(dto)
//                .setHeader(KafkaHeaders.TOPIC, KafkaProperties.SCOREBOARD_UPDATED_TOPIC)
//                .setHeader(JsonDeserializer.VALUE_DEFAULT_TYPE, dto.getClass().getName())
//                .build());
    }

    @Override
    public void scoreBoardStopped(ScoreBoard scoreBoard) {
        final var dto = ScoreBoardEventMapper.toStopped(scoreBoard);
        eventPublisher.publishEvent(dto);
//        kafkaTemplate.send(MessageBuilder.withPayload(dto)
//            .setHeader(KafkaHeaders.TOPIC, KafkaProperties.SCOREBOARD_STOPPED_TOPIC)
//            .setHeader(JsonDeserializer.VALUE_DEFAULT_TYPE, dto.getClass().getName())
//            .build());
    }
}
