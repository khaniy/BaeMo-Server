package hotil.baemo.domains.clubs.application.usecases.member.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface ExitClubUseCase {
    void exitClub(ClubId clubId, UserId userId);
}