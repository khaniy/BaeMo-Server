package hotil.baemo.domains.clubs.application.clubs.usecases.operation;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface KickUseCase {
    void kick(ClubsId clubsId, ClubsUserId actorId, ClubsUserId targetId);
}