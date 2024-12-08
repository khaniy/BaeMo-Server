package hotil.baemo.domains.clubs.domain.entity.post;

import hotil.baemo.domains.clubs.domain.aggregate.ClubPostImagesVOGroup;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostViewCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClubPost {
    private final ClubPostId clubPostId;

    private final ClubId clubId;
    private final UserId writerId;

    private ClubPostTitle clubPostTitle;
    private ClubPostContent clubPostContent;
    private ClubPostType clubPostType;
    private ClubPostImagesVOGroup clubPostImages;
    private ClubPostViewCount clubPostViewCount;

    private boolean isDelete;

    public static ClubPost of(ClubId clubId, UserId userId, ClubPostVOGroup clubPostVOGroup) {
        return ClubPost.builder()
            .clubId(clubId)
            .writerId(userId)
            .clubPostType(clubPostVOGroup.clubPostType())
            .clubPostTitle(clubPostVOGroup.clubPostTitle())
            .clubPostContent(clubPostVOGroup.clubPostContent())
            .clubPostImages(clubPostVOGroup.clubPostImagesVOGroup())
            .clubPostViewCount(new ClubPostViewCount(0L))
            .isDelete(false)
            .build();
    }

    public void update(ClubPostVOGroup clubPostVOGroup) {
        this.clubPostTitle = clubPostVOGroup.clubPostTitle();
        this.clubPostContent = clubPostVOGroup.clubPostContent();
        this.clubPostImages = clubPostVOGroup.clubPostImagesVOGroup();
    }

    public void delete() {
        this.isDelete = true;
    }

}