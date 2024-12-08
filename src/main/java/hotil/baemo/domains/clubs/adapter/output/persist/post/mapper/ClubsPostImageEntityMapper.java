package hotil.baemo.domains.clubs.adapter.output.persist.post.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostImageEntity;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;

import java.util.List;

public class ClubsPostImageEntityMapper {
    public static List<ClubsPostImageEntity> convert(ClubPost clubPost, Long clubPostId){
        return clubPost.getClubPostImages().list().stream()
            .map(e -> ClubsPostImageEntity.builder()
                .clubsPostId(clubPostId)
                .imagePath(e.clubPostImagePath().path())
                .orderNumber(e.clubPostImageOrder().order())
                .isThumbnail(e.isThumbnail())
                .build())
            .toList();
    }
}