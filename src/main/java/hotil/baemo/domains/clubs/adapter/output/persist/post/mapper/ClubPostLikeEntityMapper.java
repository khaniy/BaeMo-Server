package hotil.baemo.domains.clubs.adapter.output.persist.post.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostLikeEntity;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

import static java.lang.Boolean.TRUE;

public class ClubPostLikeEntityMapper {
    public static ClubsPostLikeEntity convert(UserId userId, ClubPostId clubPostId) {
        return ClubsPostLikeEntity.builder()
            .clubsUserId(userId.id())
            .clubsPostId(clubPostId.id())
            .isLike(TRUE)
            .build();
    }
}