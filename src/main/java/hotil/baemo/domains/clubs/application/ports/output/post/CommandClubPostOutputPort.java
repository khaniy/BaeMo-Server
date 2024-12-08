package hotil.baemo.domains.clubs.application.ports.output.post;

import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubsPostLike;

public interface CommandClubPostOutputPort {
    ClubPost save(ClubPost clubPost);

    ClubPost loadClubPost(ClubPostId clubPostId);

    void incrementViewCount(ClubPostId clubPostId);

    void delete(ClubPostId clubPostId);

    ClubsPostLike likeToggle(ClubPostId clubPostId, UserId userId);
}
