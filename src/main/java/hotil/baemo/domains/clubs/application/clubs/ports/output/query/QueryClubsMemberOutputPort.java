package hotil.baemo.domains.clubs.application.clubs.ports.output.query;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface QueryClubsMemberOutputPort {
    ClubsUserId loadDelegableUser(ClubsId clubsId);

    ClubsRole loadClubsRole(ClubsId clubsId, ClubsUserId clubsUserId);
}