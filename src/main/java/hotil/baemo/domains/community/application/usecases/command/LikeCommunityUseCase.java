package hotil.baemo.domains.community.application.usecases.command;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

public interface LikeCommunityUseCase {
    void like(CommunityId communityId, CommunityUserId communityUserId);
}
