package hotil.baemo.domains.clubs.application.usecases.club.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface UpdateClubUseCase {

    void updateClubs(UserId userId, ClubId clubId, ClubName clubName, ClubSimpleDescription clubSimpleDescription, ClubDescription clubDescription, ClubLocation clubLocation, ClubImage clubImage);
}
