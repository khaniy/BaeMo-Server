package hotil.baemo.domains.clubs.domain.post.policy.update;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class UpdateClubsPostPolicy {
    private final UpdateClubsPostAggregate aggregate;
    private final ClubsPostId clubsPostId;

    private UpdateClubsPostPolicy(UpdateClubsPostAggregate aggregate) {
        this.aggregate = aggregate;
        this.clubsPostId = aggregate.getClubsPostId();
    }

    public static UpdateClubsPostPolicy execute(UpdateClubsPostAggregate aggregate) {
        return new UpdateClubsPostPolicy(aggregate);
    }

    public UpdateClubsPostPolicy validAuthorization(BiConsumer<ClubsPostId, ClubsUserId> validAuthorization) {
        validAuthorization.accept(clubsPostId, aggregate.getClubsUserId());

        return this;
    }

    public void update(Consumer<UpdateClubsPostAggregate> update) {
        update.accept(aggregate);
    }
}