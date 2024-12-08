package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import static hotil.baemo.core.util.BaeMoObjectUtil.isNullValue;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByUserId(Long userId);

    default CategoryEntity loadOrInit(CommunityUserId communityUserId) {
        final var categoryEntity = findByUserId(communityUserId.id());

        if (isNullValue(categoryEntity)) {
            return CategoryEntity.init(communityUserId);
        }

        return categoryEntity;
    }
}