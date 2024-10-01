package hotil.baemo.domains.clubs.application.post.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface StatClubsPostOutputPort {
    void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId);
}
