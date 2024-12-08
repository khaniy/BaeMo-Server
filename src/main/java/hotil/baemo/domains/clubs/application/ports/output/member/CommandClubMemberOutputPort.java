package hotil.baemo.domains.clubs.application.ports.output.member;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface CommandClubMemberOutputPort {

    void saveUser(ClubMember clubMember);

    ClubMember loadClubUser(UserId userId, ClubId clubId);

    ClubMember loadDelegateUser(ClubId clubId);

    void delete(ClubMember clubMember);

    void deleteAllByUsersId(UserId userId);

}
