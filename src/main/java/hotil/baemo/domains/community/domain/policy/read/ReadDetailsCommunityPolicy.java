package hotil.baemo.domains.community.domain.policy.read;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;

import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ReadDetailsCommunityPolicy {
    private final CommunityId communityId;
    private final CommunityUserId communityUserId;

    private CommunityImageList communityImageList;

    private ReadDetailsCommunityPolicy(CommunityId communityId, CommunityUserId communityUserId) {
        this.communityId = communityId;
        this.communityUserId = communityUserId;
    }

    public static ReadDetailsCommunityPolicy execute(CommunityId communityId, CommunityUserId communityUserId) {
        return new ReadDetailsCommunityPolicy(communityId, communityUserId);
    }

    public ReadDetailsCommunityPolicy loadImageList(Function<CommunityId, CommunityImageList> loadImageList) {
        this.communityImageList = loadImageList.apply(this.communityId);
        return this;
    }

    public ReadDetailsCommunityPolicy incrementViewCount(BiConsumer<CommunityId, CommunityUserId> incrementViewCount) {
        incrementViewCount.accept(this.communityId, this.communityUserId);
        return this;
    }

    public <RESULT> RESULT read(final ReadDetails<CommunityId, CommunityImageList, CommunityUserId, RESULT> read) {
        return read.load(this.communityId, this.communityImageList, this.communityUserId);
    }
}