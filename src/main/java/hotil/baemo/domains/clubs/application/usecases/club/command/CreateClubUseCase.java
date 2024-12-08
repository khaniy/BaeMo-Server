package hotil.baemo.domains.clubs.application.usecases.club.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface CreateClubUseCase {

    ClubId createClubs(
        UserId userId, ClubName clubName,
        ClubSimpleDescription clubSimpleDescription,
        ClubDescription clubDescription, ClubLocation clubLocation,
        ClubImage clubImage
    );
}
