package hotil.baemo.domains.clubs.application.usecases.post.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubsPostLike;

public interface LikeClubPostUseCase {
    ClubsPostLike likeToggle(ClubPostId clubPostId, UserId userId, ClubId clubId);
}