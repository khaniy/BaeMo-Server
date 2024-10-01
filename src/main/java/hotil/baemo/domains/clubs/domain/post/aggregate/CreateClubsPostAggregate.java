package hotil.baemo.domains.clubs.domain.post.aggregate;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class CreateClubsPostAggregate {
    private final ClubsId clubsId;
    private final ClubsUserId clubsUserId;

    private final ClubsPostType clubsPostType;
    private final ClubsPostTitle clubsPostTitle;
    private final ClubsPostContent clubsPostContent;

    private final ClubsPostImageAggregateList clubsPostImageAggregateList;

    @Builder
    public CreateClubsPostAggregate(
        ClubsId clubsId, ClubsUserId clubsUserId,
        ClubsPostType clubsPostType, ClubsPostTitle clubsPostTitle,
        ClubsPostContent clubsPostContent, ClubsPostImageAggregateList clubsPostImageAggregateList
    ) {
        this.clubsId = clubsId;
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