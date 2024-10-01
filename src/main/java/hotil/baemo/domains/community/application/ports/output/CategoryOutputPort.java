package hotil.baemo.domains.community.application.ports.output;

import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;

public interface CategoryOutputPort {
    void saveSubscript(CategoryList list, CommunityUserId userId);

    CategoryList load(CommunityUserId userId);
}
