package hotil.baemo.domains.clubs.application.post.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface DeleteClubsPostUseCase {

    void delete(
            ClubsUserId clubsUserId,
            ClubsPostId clubsPostId,
            ClubsId clubsId
    );
}
