package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
