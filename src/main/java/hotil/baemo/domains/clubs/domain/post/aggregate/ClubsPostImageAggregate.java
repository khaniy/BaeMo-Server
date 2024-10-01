package hotil.baemo.domains.clubs.domain.post.aggregate;

import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImageOrder;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImagePath;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubsPostImageAggregate {
    private final ClubsPostImagePath clubsPostImagePath;

    private final ClubsPostImageOrder clubsPostImageOrder;
    private final Boolean isThumbnail;

    @Builder
    public ClubsPostImageAggregate(ClubsPostImagePath clubsPostImagePath, ClubsPostImageOrder clubsPostImageOrder, Boolean isThumbnail) {
        this.clubsPostImagePath = clubsPostImagePath;
        this.clubsPostImageOrder = clubsPostImageOrder;
        this.isThumbnail = isThumbnail;
    }

    public Long getOrder() {
        return this.clubsPostImageOrder.order();
    }

    public String getPath() {
        return this.clubsPostImagePath.path();
    }
}