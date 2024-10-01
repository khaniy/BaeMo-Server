package hotil.baemo.domains.clubs.domain.clubs.policy;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface ClubsUserRepository {
    void saveClubsUser(ClubsUser clubsUser);

    ClubsUser loadClubsUser(ClubsUserId clubsUserId, ClubsId clubsId);
}