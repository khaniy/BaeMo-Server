package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsAdmin;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubsMapper {

    public static ClubsEntity convert(Clubs domain) {
        return ClubsEntity.builder()
                .id(domain.getClubsId() != null ? domain.getClubsId().clubsId() : null)
                .creator(domain.getClubsAdmin().getClubsUserId().id())
                .clubsName(domain.getClubsName().name())
                .clubsSimpleDescription(domain.getClubsSimpleDescription().simpleDescription())
                .clubsDescription(domain.getClubsDescription().description())
                .clubsLocation(domain.getClubsLocation().location())

                .clubsBackgroundImagePath(domain.getClubsBackgroundImage().clubsBackgroundImage())
                .clubsProfileImagePath(domain.getClubsProfileImage().clubsProfileImage())

                .isDelete(domain.getClubsIsDelete() != null ? domain.getClubsIsDelete().isDeleted() : null)
                .build();
    }

    public static Clubs convert(ClubsEntity entity, ClubsMemberEntity clubsMemberEntity) {
        return Clubs.builder()
                .clubsId(new ClubsId(entity.getId()))
                .clubsAdmin(ClubsAdmin.builder().clubsUserId(new ClubsUserId(clubsMemberEntity.getUsersId())).clubsId(new ClubsId(entity.getId())).build())
                .clubsName(new ClubsName(entity.getClubsName()))
                .clubsSimpleDescription(new ClubsSimpleDescription(entity.getClubsSimpleDescription()))
                .clubsDescription(new ClubsDescription(entity.getClubsDescription()))
                .clubsLocation(new ClubsLocation(entity.getClubsLocation()))

                .clubsProfileImage(new ClubsProfileImage(entity.getClubsProfileImagePath()))
                .clubsBackgroundImage(new ClubsBackgroundImage(entity.getClubsBackgroundImagePath()))

                .clubsIsDelete(new ClubsIsDelete(entity.getIsDelete()))
                .build();
    }
}