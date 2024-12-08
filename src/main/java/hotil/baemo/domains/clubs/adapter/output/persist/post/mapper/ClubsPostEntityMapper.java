package hotil.baemo.domains.clubs.adapter.output.persist.post.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostViewCount;

public class ClubsPostEntityMapper {

    public static ClubsPostEntity convert(final ClubPost domain) {
        return ClubsPostEntity.builder()
            .clubsPostId(domain.getClubPostId() == null ? null : domain.getClubPostId().id())
            .clubsId(domain.getClubId().clubsId())
            .clubsPostWriter(domain.getWriterId().id())

            .clubsPostTitle(domain.getClubPostTitle().title())
            .clubsPostContent(domain.getClubPostContent().content())
            .clubPostType(domain.getClubPostType())
            .viewCount(domain.getClubPostViewCount().count())

            .isDelete(domain.isDelete())
            .build();
    }

    public static ClubPost convert(final ClubsPostEntity entity) {
        return ClubPost.builder()
            .clubPostId(new ClubPostId(entity.getClubsPostId()))
            .clubId(new ClubId(entity.getClubsId()))
            .writerId(new UserId(entity.getClubsPostWriter()))

            .clubPostTitle(new ClubPostTitle(entity.getClubsPostTitle()))
            .clubPostContent(new ClubPostContent(entity.getClubsPostContent()))
            .clubPostType(entity.getClubsPostType())
            .clubPostViewCount(new ClubPostViewCount(entity.getViewCount()))

            .isDelete(false)
            .build();
    }
}