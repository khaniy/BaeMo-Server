package hotil.baemo.domains.community.application.ports.output.query;

import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import org.springframework.data.domain.Pageable;

public interface QueryCommunityOutputPort {
    RetrieveCommunity.CommunityPreviewListDTO read(CommunityUserId communityUserId, Pageable pageable, CategoryList categoryList);

    RetrieveCommunity.CommunityDetails readDetails(CommunityId communityId, CommunityUserId communityUserId);

    CommunityImageList loadImageList(CommunityId communityId);
}