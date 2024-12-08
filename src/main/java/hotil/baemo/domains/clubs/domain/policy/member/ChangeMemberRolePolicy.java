package hotil.baemo.domains.clubs.domain.policy.member;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeMemberRolePolicy {

    private final UserId userId;
    private final UserId targetUserId;
    private final ClubId clubId;


    public static ChangeMemberRolePolicy execute(UserId userId, UserId targetUserId, ClubId clubId) {
        return new ChangeMemberRolePolicy(userId, targetUserId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isAdminRole()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(clubMember, targetUserId, clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubMember clubAdmin;
        private final UserId targetUserId;
        private final ClubId clubId;

        public ExecuteStep load(final BiFunction<UserId, ClubId, ClubMember> getUser) {
            ClubMember targetMember = getUser.apply(targetUserId, clubId);
            return ExecuteStep.of(clubAdmin, targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubMember clubAdmin;
        private final ClubMember targetMember;

        public PersistStep execute(ClubRole clubRole) {
            if (targetMember.isNonMember() || targetMember.isPendingMember()) {
                throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
            }
            if (clubRole.equals(ClubRole.ADMIN)) {
                clubAdmin.toManager();
            }
            targetMember.setRole(clubRole);
            return PersistStep.of(clubAdmin, targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubMember clubAdmin;
        private final ClubMember targetMember;

        public EventStep persist(final Consumer<ClubMember> saveUser) {
            saveUser.accept(clubAdmin);
            saveUser.accept(targetMember);
            return EventStep.of(targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubMember clubMember;

        public void produce(final Consumer<ClubMember> producer) {
            producer.accept(clubMember);
        }
    }
}
