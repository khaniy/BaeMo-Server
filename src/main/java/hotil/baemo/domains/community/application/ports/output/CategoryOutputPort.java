package hotil.baemo.domains.community.application.ports.output;

import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;

public interface CategoryOutputPort {
    void subscribe(CategoryList list, CommunityUserId userId);

    CategoryList load(CommunityUserId userId);
}
