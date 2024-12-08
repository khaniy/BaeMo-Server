package hotil.baemo.domains.clubs.adapter.output.persist.member.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMemberId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubMemberMapper {

    public static ClubsMemberEntity convert(ClubMember domain) {
        return ClubsMemberEntity.builder()
            .id(domain.getId() != null ? domain.getId().id() : null)
            .clubsId(domain.getClubId().clubsId())
            .usersId(domain.getUserId().id())
            .clubRole(domain.getRole())
            .build();
    }

    public static ClubMember convert(ClubsMemberEntity entity) {
        return ClubMember.builder()
            .id(new ClubMemberId(entity.getId()))
            .clubId(new ClubId(entity.getClubsId()))
            .userId(new UserId(entity.getUsersId()))
            .role(entity.getClubRole())
            .build();
    }
}
