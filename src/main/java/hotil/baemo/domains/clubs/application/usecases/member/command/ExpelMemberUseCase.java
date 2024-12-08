package hotil.baemo.domains.clubs.application.usecases.member.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface ExpelMemberUseCase {

    void expelMember(ClubId clubId, UserId actorId, UserId targetId);

    void exitAllClub(UserId userId);
}