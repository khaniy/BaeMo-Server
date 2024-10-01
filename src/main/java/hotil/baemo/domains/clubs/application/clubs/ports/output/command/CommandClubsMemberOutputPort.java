package hotil.baemo.domains.clubs.application.clubs.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface CommandClubsMemberOutputPort {
    void updateRole(ClubsId clubsId, ClubsUserId clubsUserId, ClubsRole clubsRole);
}
