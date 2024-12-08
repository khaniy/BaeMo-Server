package hotil.baemo.domains.community.application.ports.output.query;

import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;

public interface QueryCategoryOutputPort {
    CategoryList loadSubscribe(CommunityUserId communityUserId);
}
