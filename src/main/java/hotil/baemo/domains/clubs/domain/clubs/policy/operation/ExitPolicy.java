package hotil.baemo.domains.clubs.domain.clubs.policy.operation;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ExitPolicy {
    private final ClubsUser clubsUser;

    private Boolean isDelegate;
    private Boolean isDelete;

    private ClubsUserId delegableUserId;

    private ExitPolicy(ClubsUser clubsUser) {
        this.clubsUser = clubsUser;
        this.isDelegate = clubsUser.getRole() == ClubsRole.ADMIN;
        this.isDelete = false;
    }

    public static ExitPolicy execute(ClubsUser clubsUser) {
        return new ExitPolicy(clubsUser);
    }

    public ExitPolicy loadDelegableUser(Supplier<ClubsUserId> delegableUserId) {
        if (isDelegate && delegableUserId.get() != null) {
            this.delegableUserId = delegableUserId.get();
        }

        if (isDelegate && delegableUserId.get() == null) {
            this.isDelegate = false;
            this.isDelete = true;
        }

        return this;
    }

    public ExitPolicy delegate(Consumer<ClubsUserId> delegateClubs) {
        if (isDelegate) {
            delegateClubs.accept(this.delegableUserId);
        }

        return this;
    }

    public ExitPolicy delete(Runnable delete) {
        if (isDelete) {
            delete.run();
        }

        return this;
    }

    public void exit(ExitClubs exitClubs) {
        exitClubs.action();
    }
}