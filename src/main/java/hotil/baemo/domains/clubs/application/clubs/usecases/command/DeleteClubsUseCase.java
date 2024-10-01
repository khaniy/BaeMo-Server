package hotil.baemo.domains.clubs.application.clubs.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface DeleteClubsUseCase {
    void deleteClubs(ClubsId clubsId, ClubsUserId ClubsUserId);
}
