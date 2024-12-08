package hotil.baemo.domains.clubs.application.ports.output.club;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface ClubEventOutputPort {
    void sendCreatedEvent(UserId userId, ClubId clubId);

    void sendDeletedEvent(ClubId clubId);
}
