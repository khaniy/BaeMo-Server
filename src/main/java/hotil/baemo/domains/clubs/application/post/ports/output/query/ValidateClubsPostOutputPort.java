package hotil.baemo.domains.clubs.application.post.ports.output.query;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

public interface ValidateClubsPostOutputPort {
    void validDetailView(ClubsUserId clubsUserId, ClubsPostId clubsPostId);

    void validUpdateAuthorization(ClubsPostId clubsPostId, ClubsUserId clubsUserId);
}