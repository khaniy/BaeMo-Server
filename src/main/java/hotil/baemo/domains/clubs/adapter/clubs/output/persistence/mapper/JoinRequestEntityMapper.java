package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.JoinRequestEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinRequestEntityMapper {

    public static Join.Request convert(JoinRequestEntity entity) {
        return Join.Request.builder()
            .clubsNonMember(ClubsNonMember.builder()
                .clubsId(new ClubsId(entity.getClubsId()))
                .clubsUserId(new ClubsUserId(entity.getNonMemberId()))
                .build())
            .build();
    }

    public static JoinRequestEntity convert(Join.Request domain) {
        return JoinRequestEntity.builder()
            .nonMemberId(domain.clubsNonMember().getClubsUserId().id())
            .clubsId(domain.clubsNonMember().getClubsId().clubsId())
            .build();
    }
}
