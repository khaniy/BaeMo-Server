package hotil.baemo.domains.community.adapter.output.persistence;

import hotil.baemo.domains.community.adapter.output.persistence.mapper.CategoryMapper;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.application.ports.output.CategoryOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryOutputAdapter implements CategoryOutputPort {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void saveSubscript(CategoryList list, CommunityUserId userId) {
        final var categoryEntity = categoryJpaRepository.findByUserId(userId.id());
        if (categoryEntity == null) {
            categoryJpaRepository.save(CategoryMapper.convert(userId, list));
        } else {
            final var convert = CategoryMapper.convert(userId, list);
            categoryEntity.update(convert);
        }
    }

    @Override
    public CategoryList load(CommunityUserId userId) {
        final var categoryEntity = categoryJpaRepository.findByUserId(userId.id());
        return CategoryMapper.convert(categoryEntity);
    }
}
