package hotil.baemo.domains.clubs.domain.policy.club;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveClubDetailPolicy {

    private final UserId userId;
    private final ClubId clubId;


    public static RetrieveClubDetailPolicy execute(UserId userId, ClubId clubId) {
        return new RetrieveClubDetailPolicy(userId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        return LoadStep.of(clubId, clubMember);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubId clubId;
        private final ClubMember clubMember;

        public QClubDTO.ClubDetailView load(final BiFunction<ClubId, ClubMember, QClubDTO.ClubDetailView> getClub) {
            return getClub.apply(clubId, clubMember);
        }
    }
}
