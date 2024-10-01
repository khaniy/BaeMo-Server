package hotil.baemo.domains.community.application.ports.input;

import hotil.baemo.domains.community.application.ports.output.CategoryOutputPort;
import hotil.baemo.domains.community.application.usecases.CategoryUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryInputPort implements CategoryUseCase {
    private final CategoryOutputPort categoryOutputPort;

    @Override
    public CategoryList getList() {
        return CategoryList.getBaeMoList();
    }

    @Override
    public CategoryList getSubscribedList(CommunityUserId userId) {
        return categoryOutputPort.load(userId);
    }

    @Override
    public void subscribe(CategoryList list, CommunityUserId userId) {
        categoryOutputPort.saveSubscript(list, userId);
    }
}