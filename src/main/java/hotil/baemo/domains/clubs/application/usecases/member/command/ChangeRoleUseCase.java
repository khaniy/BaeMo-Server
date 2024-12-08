package hotil.baemo.domains.clubs.application.usecases.member.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;

public interface ChangeRoleUseCase {
    void change(ClubId clubId, UserId actorId, UserId targetId, ClubRole clubRole);
}