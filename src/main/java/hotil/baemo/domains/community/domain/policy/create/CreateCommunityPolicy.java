package hotil.baemo.domains.community.domain.policy.create;

import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class CreateCommunityPolicy {
    private final CommunityCreateAggregate aggregate;

    private final CommunityWriter communityWriter;
    private CommunityId communityId;

    private CreateCommunityPolicy(CommunityCreateAggregate aggregate) {
        this.aggregate = aggregate;
        this.communityWriter = aggregate.getCommunityWriter();
    }

    public static CreateCommunityPolicy execute(final CommunityCreateAggregate aggregate) {
        return new CreateCommunityPolicy(aggregate);
    }

    public CreateCommunityPolicy validAuthority(final Consumer<CommunityWriter> validAuthority) {
        validAuthority.accept(communityWriter);
        return this;
    }

    public CreateCommunityPolicy persist(final Function<CommunityCreateAggregate, CommunityId> persist) {
        this.communityId = persist.apply(aggregate);
        return this;
    }

    public CreateCommunityPolicy validThumbnailCount(final Consumer<CommunityImageList> validThumbnailCount) {
        validThumbnailCount.accept(aggregate.getCommunityImageList());
        return this;
    }

    public CommunityId persistImage(final BiConsumer<CommunityId, CommunityImageList> persistImage) {
        persistImage.accept(communityId, aggregate.getCommunityImageList());
        return communityId;
    }
}