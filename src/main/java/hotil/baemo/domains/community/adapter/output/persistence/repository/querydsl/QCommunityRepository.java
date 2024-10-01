package hotil.baemo.domains.community.adapter.output.persistence.repository.querydsl;

import hotil.baemo.domains.community.application.dto.CommunityPreview;
import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CategoryList;

import java.util.List;

public interface QCommunityRepository {
    List<CommunityPreview> loadCommunityPreview(CategoryList category);

    RetrieveCommunity.CommunityDetails loadCommunityDetails(CommunityId communityId);
}
