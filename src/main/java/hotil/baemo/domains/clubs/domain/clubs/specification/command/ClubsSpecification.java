package hotil.baemo.domains.clubs.domain.clubs.specification.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;

public interface ClubsSpecification {
    ClubsSpecification.Update updateClubs();

    ClubsSpecification.Delete deleteClubs();

    @FunctionalInterface
    interface Update {
        Clubs update(Clubs oldClubs, Clubs newClubs);
    }

    @FunctionalInterface
    interface Delete {
        Clubs delete(Clubs clubs);
    }
}