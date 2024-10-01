package hotil.baemo.domains.clubs.application.clubs.ports.output.operation;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface ExitClubsOutputPort {
    void exit(ClubsUser clubsUser);

    void delegate(ClubsId clubsId, ClubsUserId clubsUserId);
}
