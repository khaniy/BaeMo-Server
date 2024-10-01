package hotil.baemo.domains.clubs.domain.post.entity;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.post.value.*;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImages;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubsPost {
    private final ClubsId clubsId;
    private final ClubsPostId clubsPostId;
    private final ClubsPostWriter clubsPostWriter;

    private ClubsPostTitle clubsPostTitle;
    private ClubsPostContent clubsPostContent;
    private ClubsPostImages clubsPostImages;

    private ClubsPostType clubsPostType;
    private ClubsPostIsDelete clubsPostIsDelete;

    @Builder
    public ClubsPost(ClubsId clubsId, ClubsPostId clubsPostId, ClubsPostWriter clubsPostWriter, ClubsPostTitle clubsPostTitle, ClubsPostContent clubsPostContent, ClubsPostImages clubsPostImages, ClubsPostType clubsPostType, ClubsPostIsDelete clubsPostIsDelete) {
        this.clubsId = clubsId;
        this.clubsPostId = clubsPostId;
        this.clubsPostWriter = clubsPostWriter;
        this.clubsPostTitle = clubsPostTitle;
        this.clubsPostContent = clubsPostContent;
        this.clubsPostImages = clubsPostImages;
        this.clubsPostType = clubsPostType;
        this.clubsPostIsDelete = clubsPostIsDelete == null ? ClubsPostIsDelete.init() : clubsPostIsDelete;
    }

    public void updateTitle(final ClubsPostTitle newTitle) {
        this.clubsPostTitle = newTitle;
    }

    public void updateContent(final ClubsPostContent newContent) {
        this.clubsPostContent = newContent;
    }

    public void updateImages(final ClubsPostImages newImages) {
        this.clubsPostImages = newImages;
    }

    public void delete() {
        this.clubsPostIsDelete = ClubsPostIsDelete.delete();
    }

    public void updateClubsType(final ClubsPostType newClubsPostType) {
        this.clubsPostType = newClubsPostType;
    }
}