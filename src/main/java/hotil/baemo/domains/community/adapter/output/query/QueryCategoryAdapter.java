package hotil.baemo.domains.community.adapter.output.query;

import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.application.ports.output.query.QueryCategoryOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCategoryAdapter implements QueryCategoryOutputPort {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CategoryList loadSubscribe(CommunityUserId communityUserId) {
        return categoryJpaRepository.loadOrInit(communityUserId)
            .getCategoryList();
    }
}