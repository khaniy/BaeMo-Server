package hotil.baemo.domains.clubs.application.post.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface StatClubsPostUseCase {
    void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId);
}