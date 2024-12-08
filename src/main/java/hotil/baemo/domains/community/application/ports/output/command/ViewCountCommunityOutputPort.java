package hotil.baemo.domains.community.application.ports.output.command;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

public interface ViewCountCommunityOutputPort {
    void incrementViewCount(CommunityId communityId, CommunityUserId communityUserId);
}