package hotil.baemo.domains.community.domain.policy.like;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;

import java.util.function.BiConsumer;

public final class LikeCommunityPolicy {
    private final CommunityId communityId;
    private final CommunityUserId communityUserId;

    private LikeCommunityPolicy(CommunityId communityId, CommunityUserId communityUserId) {
        this.communityId = communityId;
        this.communityUserId = communityUserId;
    }

    public static LikeCommunityPolicy execute(CommunityId communityId, CommunityUserId communityUserId) {
        return new LikeCommunityPolicy(communityId, communityUserId);
    }

    public LikeCommunityPolicy validAuthority(BiConsumer<CommunityId, CommunityUserId> validAuthority) {
        validAuthority.accept(this.communityId, this.communityUserId);
        return this;
    }

    public void toggle(BiConsumer<CommunityId, CommunityUserId> toggle) {
        toggle.accept(this.communityId, this.communityUserId);
    }
}