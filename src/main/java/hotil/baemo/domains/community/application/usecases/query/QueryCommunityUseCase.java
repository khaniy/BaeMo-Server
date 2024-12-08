package hotil.baemo.domains.community.application.usecases.query;

import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import org.springframework.data.domain.Pageable;

public interface QueryCommunityUseCase {
    RetrieveCommunity.CommunityPreviewListDTO read(CommunityUserId communityUserId, Pageable pageable, CategoryList categoryList);

    RetrieveCommunity.CommunityPreviewListDTO readSubscribe(CommunityUserId communityUserId, Pageable pageable);

    RetrieveCommunity.ReadCommunityDetails readDetails(CommunityId communityId, CommunityUserId communityUserId);
}
