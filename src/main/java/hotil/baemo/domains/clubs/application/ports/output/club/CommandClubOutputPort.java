package hotil.baemo.domains.clubs.application.ports.output.club;

import hotil.baemo.domains.clubs.domain.entity.club.Club;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.ClubImage;

public interface CommandClubOutputPort {
    void deleteClub(Club club);

    void deleteClub(ClubId clubId);

    ClubId saveClub(Club club, ClubImage clubImage);

    Club loadClub(ClubId clubId);
}
