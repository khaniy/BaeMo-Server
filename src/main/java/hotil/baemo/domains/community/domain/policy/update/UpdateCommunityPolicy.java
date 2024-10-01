package hotil.baemo.domains.community.domain.policy.update;

import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class UpdateCommunityPolicy {
    private final CommunityUpdateAggregate aggregate;
    private final CommunityId communityId;

    private UpdateCommunityPolicy(CommunityUpdateAggregate aggregate) {
        this.aggregate = aggregate;
        this.communityId = aggregate.getCommunityId();
    }

    public static UpdateCommunityPolicy execute(final CommunityUpdateAggregate aggregate) {
        return new UpdateCommunityPolicy(aggregate);
    }

    public UpdateCommunityPolicy validAuthority(final Consumer<CommunityId> validAuthority) {
        validAuthority.accept(communityId);
        return this;
    }

    public UpdateCommunityPolicy validThumbnailCount(final Consumer<CommunityImageList> validThumbnailCount) {
        validThumbnailCount.accept(aggregate.getCommunityImageList());
        return this;
    }

    public UpdateCommunityPolicy updateImage(final BiConsumer<CommunityId, CommunityImageList> persistImage) {
        persistImage.accept(communityId, aggregate.getCommunityImageList());
        return this;
    }

    public void update(final Consumer<CommunityUpdateAggregate> update) {
        update.accept(aggregate);
    }
}