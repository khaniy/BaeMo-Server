package hotil.baemo.domains.relation.adapter.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.adapter.output.persistence.mapper.RelationEntityMapper;
import hotil.baemo.domains.relation.adapter.output.persistence.repository.CommandRelationJpaRepository;
import hotil.baemo.domains.relation.application.ports.output.CommandRelationOutputPort;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandRelationPersistenceAdapter implements CommandRelationOutputPort {
    private final CommandRelationJpaRepository commandRelationJpaRepository;

    @Override
    public void save(Relation relation) {
        commandRelationJpaRepository.save(RelationEntityMapper.toEntity(relation));
    }

    @Override
    public Relation getRelation(RelationId id, UserId userId) {
        var relation = commandRelationJpaRepository.findByIdAndUserId(id.id(), userId.id()).orElseThrow(
            () -> new CustomException(ResponseCode.RELATION_NOT_FOUND));
        return RelationEntityMapper.toDomain(relation);
    }

    @Override
    public Optional<Relation> getRelation(UserId userId, UserId targetId) {
        return commandRelationJpaRepository.findByUserAndTargetId(userId.id(), targetId.id())
            .map(RelationEntityMapper::toDomain);
    }

    @Override
    public void delete(Relation relation) {
        commandRelationJpaRepository.delete(RelationEntityMapper.toEntity(relation));
    }
}