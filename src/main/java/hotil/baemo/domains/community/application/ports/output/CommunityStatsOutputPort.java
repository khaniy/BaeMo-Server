package hotil.baemo.domains.community.application.ports.output;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

public interface CommunityStatsOutputPort {
    void incrementViewCount(CommunityId communityId, CommunityUserId communityUserId);

    void executeLike(CommunityId communityId, CommunityUserId communityUserId);
}
