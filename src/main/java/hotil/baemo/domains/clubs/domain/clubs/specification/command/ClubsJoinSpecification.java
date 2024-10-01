package hotil.baemo.domains.clubs.domain.clubs.specification.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;

public interface ClubsJoinSpecification {
    ExecuteJoin executeJoin();

    ExecuteHandle executeHandle();

    @FunctionalInterface
    interface ExecuteJoin {
        Join.Request joinRequest(ClubsId clubsId);
    }

    @FunctionalInterface
    interface ExecuteHandle {
        Join.Result handle(ClubsNonMember clubsNonMember, Boolean isAccept);
    }
}