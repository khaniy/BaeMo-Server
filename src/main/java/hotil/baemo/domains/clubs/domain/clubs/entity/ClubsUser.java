package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsJoinSpecification;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

public interface ClubsUser extends ClubsSpecification, ClubsJoinSpecification {
    ClubsId getClubsId();

    ClubsRole getRole();

    ClubsUserId getClubsUserId();
}