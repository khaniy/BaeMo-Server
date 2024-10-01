package hotil.baemo.domains.community.application.usecases;

import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

public interface QueryCommunityUseCase {
    RetrieveCommunity.CommunityPreviewListDTO retrievePreviewList(CommunityUserId user);

    RetrieveCommunity.CommunityDetails retrieveDetails(CommunityId communityId, CommunityUserId communityUserId);
}