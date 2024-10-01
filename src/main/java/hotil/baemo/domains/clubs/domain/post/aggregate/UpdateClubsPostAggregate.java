package hotil.baemo.domains.clubs.domain.post.aggregate;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class UpdateClubsPostAggregate {
    private final ClubsPostId clubsPostId;
    private final ClubsUserId clubsUserId;

    private final ClubsPostType clubsPostType;
    private final ClubsPostTitle clubsPostTitle;
    private final ClubsPostContent clubsPostContent;

    private final ClubsPostImageAggregateList clubsPostImageAggregateList;

    @Builder
    public UpdateClubsPostAggregate(
        ClubsPostId clubsPostId, ClubsUserId clubsUserId,
        ClubsPostType clubsPostType, ClubsPostTitle clubsPostTitle,
        ClubsPostContent clubsPostContent, ClubsPostImageAggregateList clubsPostImageAggregateList
    ) {
        this.clubsPostId = clubsPostId;
        this.clubsUserId = clubsUserId;
        this.clubsPostType = clubsPostType;
        this.clubsPostTitle = clubsPostTitle;
        this.clubsPostContent = clubsPostContent;
        this.clubsPostImageAggregateList = clubsPostImageAggregateList;
    }

    public Stream<ClubsPostImageAggregate> getClubsPostImageAggregateListStream() {
        return clubsPostImageAggregateList.stream();
    }
}