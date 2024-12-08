package hotil.baemo.domains.community.application.ports.input.category;

import hotil.baemo.domains.community.application.ports.output.CategoryOutputPort;
import hotil.baemo.domains.community.application.usecases.category.CategoryUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryInputPort implements CategoryUseCase {
    private final CategoryOutputPort categoryOutputPort;

    @Override
    public CategoryList getList() {
        return CategoryList.initAllList();
    }

    @Override
    public CategoryList getSubscribedList(CommunityUserId userId) {
        return categoryOutputPort.load(userId);
    }

    @Override
    public void subscribe(CategoryList list, CommunityUserId userId) {
        categoryOutputPort.subscribe(list, userId);
    }
}