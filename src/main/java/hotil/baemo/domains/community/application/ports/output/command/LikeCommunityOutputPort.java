package hotil.baemo.domains.community.application.ports.output.command;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

public interface LikeCommunityOutputPort {
    void toggle(CommunityId communityId, CommunityUserId communityUserId);
}
