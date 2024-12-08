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
public class ApproveMemberPolicy {

    private final UserId userId;
    private final UserId targetUserId;
    private final ClubId clubId;


    public static ApproveMemberPolicy execute(UserId userId, UserId targetUserId, ClubId clubId) {
        return new ApproveMemberPolicy(userId, targetUserId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isManagerRole()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(targetUserId, clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final UserId targetUserId;
        private final ClubId clubId;

        public ExecuteStep load(final BiFunction<UserId, ClubId, ClubMember> getUser) {
            ClubMember targetMember = getUser.apply(targetUserId, clubId);
            return ExecuteStep.of(targetMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubMember clubMember;

        public PersistStep execute() {
            if (!clubMember.isPendingMember()) {
                throw new CustomException(ResponseCode.CLUBS_NOT_FOUND_APPLIED_MEMBER);
            }
            clubMember.toMember();
            return PersistStep.of(clubMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubMember clubMember;

        public EventStep persist(final Consumer<ClubMember> saveUser) {
            saveUser.accept(clubMember);
            return EventStep.of(clubMember);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubMember clubMember;

        public void produce(final BiConsumer<UserId, ClubId> producer) {
            producer.accept(clubMember.getUserId(), clubMember.getClubId());
        }
    }
}
