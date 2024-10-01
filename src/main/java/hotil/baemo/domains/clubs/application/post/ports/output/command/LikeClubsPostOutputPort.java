package hotil.baemo.domains.clubs.application.post.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;

public interface LikeClubsPostOutputPort {
    ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUser clubsUser);
}
