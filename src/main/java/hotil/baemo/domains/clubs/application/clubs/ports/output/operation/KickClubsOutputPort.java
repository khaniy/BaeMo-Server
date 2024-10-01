package hotil.baemo.domains.clubs.application.clubs.ports.output.operation;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;

public interface KickClubsOutputPort {
    void kick(ClubsUser target);
}
