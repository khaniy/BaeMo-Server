package hotil.baemo.domains.clubs.domain.policy.post;

import hotil.baemo.domains.clubs.domain.aggregate.ClubPostImagesVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

@FunctionalInterface
public interface UploadClubsPostImageList {
    void upload(ClubPostId clubPostId, ClubPostImagesVOGroup clubPostImagesVOGroup);
}
