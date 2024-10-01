package hotil.baemo.domains.clubs.application.clubs.usecases.operation;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface ChangeRoleUseCase {
    void change(ClubsId clubsId, ClubsUserId actorId, ClubsUserId targetId, ClubsRole clubsRole);
}