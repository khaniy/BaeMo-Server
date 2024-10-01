package hotil.baemo.domains.clubs.application.clubs.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface JoinClubsUseCase {
    void join(ClubsUserId clubsUserId, ClubsId clubsId);

    void handle(ClubsUserId clubsUserId, ClubsId clubsId, ClubsNonMember clubsNonMember, Boolean isAccept);
}
