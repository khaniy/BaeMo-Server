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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpelMemberPolicy {

    private final UserId userId;
    private final UserId targetUserId;
    private final ClubId clubId;


    public static ExpelMemberPolicy execute(UserId userId, UserId targetUserId, ClubId clubId) {
        return new ExpelMemberPolicy(userId, targetUserId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isManagerRole()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(clubMember, targetUserId, clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubMember clubMember;
        private final UserId targetUserId;
        private final ClubId clubId;

        public ExecuteStep load(final BiFunction<UserId, ClubId, ClubMember> getUser) {
            ClubMember targetMember = getUser.apply(targetUserId, clubId);
            return ExecuteStep.of(clubMember, targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubMember clubMember;
        private final ClubMember targetMember;

        public PersistStep execute() {
            if(clubMember.getRolePoint() < targetMember.getRolePoint()){
                throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
            }
            return PersistStep.of(targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubMember targetMember;

        public EventStep persist(
            final Consumer<ClubMember> deleteUser
        ) {
            deleteUser.accept(targetMember);
            return EventStep.of(targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubMember delegateUser;

        public void produce(final BiConsumer<UserId, ClubId> producer) {
            producer.accept(delegateUser.getUserId(), delegateUser.getClubId());

        }
    }
}
