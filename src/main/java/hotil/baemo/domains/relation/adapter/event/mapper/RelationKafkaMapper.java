package hotil.baemo.domains.relation.adapter.event.mapper;

import hotil.baemo.domains.relation.adapter.event.dto.KafkaDTO;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;

public class RelationKafkaMapper {
    public static KafkaDTO.Friend toRelationKafkaDTO(Relation relation) {
        return new KafkaDTO.Friend(relation.getUserId().id(), relation.getTargetId().id());
    }

    public static KafkaDTO.Friend toRelationKafkaDTO(UserId userId, UserId targetId) {
        return new KafkaDTO.Friend(userId.id(), targetId.id());
    }
}
