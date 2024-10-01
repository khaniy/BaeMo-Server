package hotil.baemo.domains.clubs.domain.post.policy.cretae;

import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

@FunctionalInterface
public interface CreateClubsPost {
    ClubsPostId create(CreateClubsPostAggregate createClubsPostAggregate);
}
