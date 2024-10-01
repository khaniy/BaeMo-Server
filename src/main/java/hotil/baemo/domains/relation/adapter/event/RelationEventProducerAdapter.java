package hotil.baemo.domains.relation.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.relation.adapter.event.dto.KafkaDTO;
import hotil.baemo.domains.relation.adapter.event.mapper.RelationKafkaMapper;
import hotil.baemo.domains.relation.application.ports.output.RelationEventOutPort;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationEventProducerAdapter implements RelationEventOutPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void friendDeleted(UserId userId, UserId targetId) {
        KafkaDTO.Friend dto = RelationKafkaMapper.toRelationKafkaDTO(userId, targetId);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.RELATION_DELETED, message);
    }

    @Override
    public void friendDeleted(Relation relation) {
        KafkaDTO.Friend dto = RelationKafkaMapper.toRelationKafkaDTO(relation);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.RELATION_DELETED, message);
    }
}
