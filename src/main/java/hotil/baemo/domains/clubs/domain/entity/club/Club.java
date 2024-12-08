package hotil.baemo.domains.clubs.domain.entity.club;

import hotil.baemo.domains.clubs.domain.value.club.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Club {
    private final ClubId clubId;
    private ClubName clubName;
    private ClubSimpleDescription clubSimpleDescription;
    private ClubDescription clubDescription;
    private ClubLocation clubLocation;
    private ClubImageUrl clubProfileImageUrl;
    private ClubImageUrl clubBackgroundImageUrl;
    private ClubImageUrl clubThumbnailImageUrl;

    private boolean isDelete;

    @Builder
    public Club(ClubId clubId, ClubName clubName, ClubSimpleDescription clubSimpleDescription, ClubDescription clubDescription, ClubLocation clubLocation, ClubImageUrl clubProfileImageUrl, ClubImageUrl clubThumbnailImageUrl, ClubImageUrl clubBackgroundImageUrl, boolean isDelete) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubSimpleDescription = clubSimpleDescription;
        this.clubDescription = clubDescription;
        this.clubLocation = clubLocation;
        this.clubProfileImageUrl = clubProfileImageUrl;
        this.clubBackgroundImageUrl = clubBackgroundImageUrl;
        this.clubThumbnailImageUrl = clubThumbnailImageUrl;
        this.isDelete = isDelete;
    }


    public void updateClubsName(ClubName clubName) {
        this.clubName = clubName;
    }

    public void updateClubsSimpleDescription(ClubSimpleDescription clubSimpleDescription) {
        this.clubSimpleDescription = clubSimpleDescription;
    }

    public void updateClubsDescription(ClubDescription clubDescription) {
        this.clubDescription = clubDescription;

    }

    public void updateClubsLocation(ClubLocation clubLocation) {
        this.clubLocation = clubLocation;
    }

    public void updateClubProfileImageUrl(ClubImageUrl clubImageUrl) {
        this.clubProfileImageUrl = clubImageUrl;
    }

    public void updateClubBackgroundImageUrl(ClubImageUrl clubImageUrl) {
        this.clubBackgroundImageUrl = clubImageUrl;
    }

    public void updateClubThumbnailImageUrl(ClubImageUrl clubImageUrl) {
        this.clubThumbnailImageUrl = clubImageUrl;
    }

    public Club delete() {
        this.isDelete = true;
        return this;
    }
}