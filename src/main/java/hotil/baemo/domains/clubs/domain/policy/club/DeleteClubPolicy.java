package hotil.baemo.domains.clubs.domain.policy.club;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.Club;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteClubPolicy {

    private final UserId userId;
    private final ClubId clubId;


    public static DeleteClubPolicy execute(UserId userId, ClubId clubId) {
        return new DeleteClubPolicy(userId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isAdminRole()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubId clubId;

        public ExecuteStep load(final Function<ClubId, Club> getClub) {
            Club club = getClub.apply(clubId);
            return ExecuteStep.of(club);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Club club;

        public PersistStep execute(){
            club.delete();
            return PersistStep.of(club);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Club club;

        public EventStep persist(final Consumer<Club> deleteClub) {
            deleteClub.accept(club);
            return EventStep.of(club);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Club club;

        public void produce(final Consumer<ClubId> clubDeleted) {
            clubDeleted.accept(club.getClubId());
        }
    }
}
