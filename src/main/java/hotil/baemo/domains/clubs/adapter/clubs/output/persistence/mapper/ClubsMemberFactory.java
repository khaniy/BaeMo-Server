package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.*;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
enum ClubsMemberFactory {
    ADMIN_FACTORY(clubsRole -> clubsRole == ClubsRole.ADMIN, clubsUser -> ClubsAdmin.builder().clubsUserId(new ClubsUserId(clubsUser.getUsersId())).clubsId(new ClubsId(clubsUser.getClubsId())).build()),
    MANAGER_FACTORY(clubsRole -> clubsRole == ClubsRole.MANAGER, clubsUser -> ClubsManager.builder().clubsUserId(new ClubsUserId(clubsUser.getUsersId())).clubsId(new ClubsId(clubsUser.getClubsId())).build()),
    MEMBER_FACTORY(clubsRole -> clubsRole == ClubsRole.MEMBER, clubsUser -> ClubsMember.builder().clubsUserId(new ClubsUserId(clubsUser.getUsersId())).clubsId(new ClubsId(clubsUser.getClubsId())).build()),
    NON_MEMBER_FACTORY(clubsRole -> clubsRole == ClubsRole.NON_MEMBER, clubsUser -> ClubsNonMember.builder().clubsUserId(new ClubsUserId(clubsUser.getUsersId())).clubsId(new ClubsId(clubsUser.getClubsId())).build()),

    ;

    private final Predicate<ClubsRole> checkRole;
    private final Function<ClubsMemberEntity, ClubsUser> convert;
}
