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
public class ApplyClubPolicy {

    private final UserId userId;
    private final ClubId clubId;


    public static ApplyClubPolicy execute(UserId userId, ClubId clubId) {
        return new ApplyClubPolicy(userId, clubId);
    }

    public ExecuteStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isNonMember()) {
            throw new CustomException(ResponseCode.CLUBS_ALREADY_APPLIED);
        }
        return ExecuteStep.of(clubMember);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubMember clubMember;

        public PersistStep execute() {
            clubMember.toPendingMember();
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

        public void produce(final BiConsumer<ClubId, UserId> producer) {
            producer.accept(clubMember.getClubId(), clubMember.getUserId());
        }
    }
}
