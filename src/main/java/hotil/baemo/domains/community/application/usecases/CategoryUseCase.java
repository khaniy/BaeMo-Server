package hotil.baemo.domains.community.application.usecases;

import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;

public interface CategoryUseCase {
    CategoryList getList();

    CategoryList getSubscribedList(CommunityUserId userId);

    void subscribe(CategoryList list, CommunityUserId userId);
}