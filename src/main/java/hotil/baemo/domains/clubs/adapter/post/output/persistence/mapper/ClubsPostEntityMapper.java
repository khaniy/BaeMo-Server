package hotil.baemo.domains.clubs.adapter.post.output.persistence.mapper;

import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostIsDelete;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import org.springframework.stereotype.Service;

@Service
public class ClubsPostEntityMapper {

    public ClubsPostEntity convert(final CreateClubsPostAggregate aggregate){
        return ClubsPostEntity.builder()
            .clubsId(aggregate.getClubsId().clubsId())
            .clubsPostWriter(aggregate.getClubsUserId().id())

            .clubsPostTitle(aggregate.getClubsPostTitle().title())
            .clubsPostContent(aggregate.getClubsPostContent().content())

            .clubsPostType(aggregate.getClubsPostType())

            .build();
    }

    public ClubsPostEntity convert(final ClubsPost domain) {
        return ClubsPostEntity.builder()
            .clubsPostId(domain.getClubsPostId() == null ? null : domain.getClubsPostId().id())
            .clubsId(domain.getClubsId().clubsId())
            .clubsPostWriter(domain.getClubsPostWriter().id())

            .clubsPostTitle(domain.getClubsPostTitle().title())
            .clubsPostContent(domain.getClubsPostContent().content())

            .clubsPostType(domain.getClubsPostType())
            .isDelete(domain.getClubsPostIsDelete().isDelete())
            .build();
    }

    public ClubsPost convert(final ClubsPostEntity entity) {
        return ClubsPost.builder()
            .clubsId(new ClubsId(entity.getClubsId()))
            .clubsPostId(new ClubsPostId(entity.getClubsPostId()))
            .clubsPostWriter(new ClubsPostWriter(entity.getClubsPostWriter()))

            .clubsPostTitle(new ClubsPostTitle(entity.getClubsPostTitle()))
            .clubsPostContent(new ClubsPostContent(entity.getClubsPostContent()))

            .clubsPostIsDelete(new ClubsPostIsDelete(entity.getIsDelete()))

            .build();
    }
}