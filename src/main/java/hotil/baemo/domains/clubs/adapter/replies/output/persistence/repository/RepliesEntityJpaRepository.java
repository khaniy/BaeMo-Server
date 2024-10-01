package hotil.baemo.domains.clubs.adapter.replies.output.persistence.repository;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.RepliesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepliesEntityJpaRepository extends JpaRepository<RepliesEntity, Long> {
}
