package hotil.baemo.domains.clubs.application.post.ports.output.command;

import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface CommandClubsPostOutputPort {
    ClubsPostId save(ClubsPost clubsPost);

    void delete(ClubsPost clubsPost);

    void incrementViewCount(ClubsPostId clubsPostId);

    ClubsPostId save(CreateClubsPostAggregate createClubsPostAggregate);

    void update(UpdateClubsPostAggregate updateClubsPostAggregate);
}
