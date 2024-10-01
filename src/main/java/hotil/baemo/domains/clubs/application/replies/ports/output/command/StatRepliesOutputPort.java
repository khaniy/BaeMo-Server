package hotil.baemo.domains.clubs.application.replies.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;

public interface StatRepliesOutputPort {
    void executeLike(RepliesId repliesId, ClubsUserId clubsUserId);
}
