package hotil.baemo.domains.match.adapter.event;


import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.match.adapter.event.dto.MatchEventDTO;
import hotil.baemo.domains.match.adapter.event.mapper.MatchEventMapper;
import hotil.baemo.domains.match.application.port.output.MatchEventOutPort;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class MatchEventProducerAdapter implements MatchEventOutPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void matchStatusUpdated(Match match) {
        if (EnumSet.of(MatchStatus.NEXT, MatchStatus.PROGRESS).contains(match.getMatchStatus())) {
            MatchEventDTO.StatusUpdated dto = MatchEventMapper.toStatusUpdated(match);
            String message = BaeMoObjectUtil.writeValueAsString(dto);
            kafkaTemplate.send(KafkaProperties.MATCH_STATUS_UPDATED, message);
        }
    }
}
