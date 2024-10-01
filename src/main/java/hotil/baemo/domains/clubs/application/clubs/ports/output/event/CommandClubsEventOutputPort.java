package hotil.baemo.domains.clubs.application.clubs.ports.output.event;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface CommandClubsEventOutputPort {
    void sendCreatedEvent(ClubsUserId clubsUserId, ClubsId clubsId);

    void sendDeletedEvent(ClubsId clubsId);

    void sendJoinUserEvent(ClubsUserId clubsUserId, ClubsId clubsId);

    void sendKickUserEvent(ClubsUserId clubsUserId, ClubsId clubsId);

    void sendUpdateUserRoleEvent(ClubsId clubsId, ClubsUserId clubsUserId, ClubsRole clubsRole);

    void sendJoinRequestEvent(ClubsId clubsId, ClubsUserId clubsUserId);

}
