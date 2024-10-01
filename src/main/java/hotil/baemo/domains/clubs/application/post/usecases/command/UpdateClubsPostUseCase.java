package hotil.baemo.domains.clubs.application.post.usecases.command;

import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;

public interface UpdateClubsPostUseCase {
    void update(UpdateClubsPostAggregate updateClubsPostAggregate);
}