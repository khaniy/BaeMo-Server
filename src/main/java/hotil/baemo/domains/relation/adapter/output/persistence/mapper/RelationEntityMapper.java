package hotil.baemo.domains.relation.adapter.output.persistence.mapper;

import hotil.baemo.domains.relation.adapter.output.persistence.entity.RelationEntity;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RelationEntityMapper {

    public static Relation toDomain(RelationEntity entity) {
        return Relation.builder()
            .id(new RelationId(entity.getId()))
            .userId(new UserId(entity.getUserId()))
            .targetId(new UserId(entity.getTargetId()))
            .type(entity.getType())
            .status(entity.getStatus())
            .build();
    }

    public static RelationEntity toEntity(Relation domain) {
        return RelationEntity.builder()
            .id(domain.getId() != null ? domain.getId().id() : null)
            .userId(domain.getUserId().id())
            .targetId(domain.getTargetId().id())
            .type(domain.getType())
            .status(domain.getStatus())
            .build();
    }
}
