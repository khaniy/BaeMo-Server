package hotil.baemo.domains.clubs.domain.policy.member;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExitClubPolicy {

    private final UserId userId;
    private final ClubId clubId;


    public static ExitClubPolicy execute(UserId userId, ClubId clubId) {
        return new ExitClubPolicy(userId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember exitMember = getUser.apply(userId, clubId);
        if(exitMember.isNonMember()){
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(exitMember, clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubMember exitMember;
        private final ClubId clubId;

        public ExecuteStep load(final Function<ClubId, ClubMember> getUser) {
            ClubMember delegateUser = null;
            if (exitMember.isAdminRole()) {
                delegateUser = getUser.apply(clubId);
            }
            return ExecuteStep.of(exitMember, delegateUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubMember exitMember;
        private final ClubMember delegateUser;

        public PersistStep execute() {
            if (delegateUser != null) {
                delegateUser.toAdmin();
            }
            return PersistStep.of(exitMember, delegateUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubMember exitMember;
        private final ClubMember delegateUser;

        public EventStep persist(
            final Consumer<ClubMember> deleteUser,
            final Consumer<ClubMember> saveUser,
            final Consumer<ClubId> deleteClub
        ) {
            deleteUser.accept(exitMember);
            if (delegateUser != null) {
                saveUser.accept(delegateUser);
            }
            if (delegateUser == null && exitMember.isAdminRole()) {
                deleteClub.accept(exitMember.getClubId());
            }
            return EventStep.of(exitMember, delegateUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubMember exitMember;
        private final ClubMember delegateUser;

        public void produce(
            final BiConsumer<UserId, ClubId> userExit,
            final Consumer<ClubMember> userRoleChanged
        ) {
            userExit.accept(exitMember.getUserId(), exitMember.getClubId());
            if (delegateUser != null) {
                userRoleChanged.accept(delegateUser);
            }
        }
    }
}
