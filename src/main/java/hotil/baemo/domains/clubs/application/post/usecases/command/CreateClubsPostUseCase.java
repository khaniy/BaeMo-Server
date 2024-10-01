package hotil.baemo.domains.clubs.application.post.usecases.command;

import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface CreateClubsPostUseCase {

    ClubsPostId create(CreateClubsPostAggregate createClubsPostAggregate);
}