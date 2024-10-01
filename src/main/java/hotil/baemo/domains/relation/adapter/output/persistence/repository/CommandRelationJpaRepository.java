package hotil.baemo.domains.relation.adapter.output.persistence.repository;

import hotil.baemo.domains.relation.adapter.output.persistence.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommandRelationJpaRepository extends JpaRepository<RelationEntity, Long> {

    @Query("SELECT e FROM RelationEntity e WHERE e.userId=:userId AND e.targetId=:targetId AND e.isDel=false ORDER BY e.updatedAt DESC")
    Optional<RelationEntity> findByUserAndTargetId(@Param("userId") Long userId, @Param("targetId") Long targetId);

    @Override
    @Query("SELECT e FROM RelationEntity e WHERE e.id=:id AND e.isDel=false")
    Optional<RelationEntity> findById(@Param("id") Long id);

    @Query("SELECT e FROM RelationEntity e WHERE e.id=:id AND e.userId=:userId AND e.isDel=false")
    Optional<RelationEntity> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
