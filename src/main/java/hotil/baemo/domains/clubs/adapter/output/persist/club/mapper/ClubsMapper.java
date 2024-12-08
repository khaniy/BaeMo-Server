package hotil.baemo.domains.clubs.adapter.output.persist.club.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.ClubsEntity;
import hotil.baemo.domains.clubs.domain.entity.club.Club;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubsMapper {

    public static ClubsEntity convert(Club domain) {
        return ClubsEntity.builder()
            .id(domain.getClubId() != null ? domain.getClubId().clubsId() : null)
            .clubsName(domain.getClubName().name())
            .clubsSimpleDescription(domain.getClubSimpleDescription().simpleDescription())
            .clubsDescription(domain.getClubDescription().description())
            .clubsLocation(domain.getClubLocation().location())

            .clubsThumbnailImagePath(domain.getClubThumbnailImageUrl().url())
            .clubsProfileImagePath(domain.getClubProfileImageUrl().url())
            .clubsBackgroundImagePath(domain.getClubBackgroundImageUrl().url())
            .isDelete(domain.isDelete())
            .build();
    }

    public static Club convert(ClubsEntity entity) {
        return Club.builder()
            .clubId(new ClubId(entity.getId()))
            .clubName(new ClubName(entity.getClubsName()))
            .clubSimpleDescription(new ClubSimpleDescription(entity.getClubsSimpleDescription()))
            .clubDescription(new ClubDescription(entity.getClubsDescription()))
            .clubLocation(new ClubLocation(entity.getClubsLocation()))

            .clubThumbnailImageUrl(new ClubImageUrl(entity.getClubsThumbnailImagePath()))
            .clubProfileImageUrl(new ClubImageUrl(entity.getClubsProfileImagePath()))
            .clubBackgroundImageUrl(new ClubImageUrl(entity.getClubsBackgroundImagePath()))
            .isDelete(entity.getIsDelete())
            .build();
    }
}