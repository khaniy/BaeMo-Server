package hotil.baemo.domains.clubs.application.ports.output.member;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface ClubMemberEventOutputPort {

    void sendJoinUserEvent(UserId userId, ClubId clubId);

    void sendExpelUserEvent(UserId userId, ClubId clubId);

    void sendExitUserEvent(UserId userId, ClubId clubId);

    void sendUpdateUserRoleEvent(ClubMember clubMember);

    void sendAppliedEvent(ClubId clubId, UserId userId);

}
