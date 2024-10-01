package hotil.baemo.domains.score.adapter.event.kafka;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.domains.score.adapter.event.dto.ScoreBoardEventMapper;
import hotil.baemo.domains.score.application.port.output.ScoreBoardEventOutPort;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreBoardEventProducerAdapter implements ScoreBoardEventOutPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void scoreBoardUpdated(ScoreBoard scoreBoard) {
        final var dto = ScoreBoardEventMapper.toEventDTO(scoreBoard);
        kafkaTemplate.send(MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.TOPIC, KafkaProperties.SCOREBOARD_UPDATED_TOPIC)
                .setHeader(JsonDeserializer.VALUE_DEFAULT_TYPE, dto.getClass().getName())
                .build());
    }

    @Override
    public void scoreBoardStopped(ScoreBoard scoreBoard) {
        final var dto = ScoreBoardEventMapper.toEventDTO(scoreBoard);
        kafkaTemplate.send(MessageBuilder.withPayload(dto)
            .setHeader(KafkaHeaders.TOPIC, KafkaProperties.SCOREBOARD_STOPPED_TOPIC)
            .setHeader(JsonDeserializer.VALUE_DEFAULT_TYPE, dto.getClass().getName())
            .build());
    }
}
