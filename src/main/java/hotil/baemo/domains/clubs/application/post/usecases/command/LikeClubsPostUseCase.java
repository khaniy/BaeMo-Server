package hotil.baemo.domains.clubs.application.post.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;

public interface LikeClubsPostUseCase {
    ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUserId clubsUserId, ClubsId clubsId);
}