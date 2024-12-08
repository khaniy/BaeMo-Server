package hotil.baemo.domains.clubs.application.usecases.club.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface DeleteClubUseCase {
    void deleteClubs(ClubId clubId, UserId UserId);
}
