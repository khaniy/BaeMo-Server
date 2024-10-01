package hotil.baemo.domains.clubs.domain.post.policy.cretae;

import hotil.baemo.domains.clubs.domain.post.aggregate.ClubsPostImageAggregateList;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;

@FunctionalInterface
public interface UploadClubsPostImageList {
    void upload(ClubsPostId clubsPostId, ClubsPostImageAggregateList clubsPostImageAggregateList);
}
