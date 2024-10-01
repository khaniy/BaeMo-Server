package hotil.baemo.domains.clubs.application.post.ports.output.event;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface CommandClubsPostEventOutputPort {

    void sendCreatedEvent(ClubsUserId clubsUserId, ClubsId clubsId);
    void sendRepliedEvent(ClubsUserId clubsUserId, ClubsId clubsId);

}
