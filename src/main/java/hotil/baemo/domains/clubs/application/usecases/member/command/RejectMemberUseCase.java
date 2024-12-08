package hotil.baemo.domains.clubs.application.usecases.member.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface RejectMemberUseCase {

    void rejectMember(UserId userId, ClubId clubId, UserId targetId);
}
