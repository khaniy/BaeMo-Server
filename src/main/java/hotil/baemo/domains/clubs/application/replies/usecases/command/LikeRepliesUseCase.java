package hotil.baemo.domains.clubs.application.replies.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;

public interface LikeRepliesUseCase {
    void like(RepliesId repliesId, ClubsUserId clubsUserId);
}
