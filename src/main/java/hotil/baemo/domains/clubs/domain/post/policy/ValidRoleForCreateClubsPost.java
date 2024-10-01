package hotil.baemo.domains.clubs.domain.post.policy;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

@FunctionalInterface
public interface ValidRoleForCreateClubsPost {
    ClubsRole getClubsRole(ClubsId clubsId, ClubsUserId clubsUserId);
}