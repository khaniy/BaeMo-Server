package hotil.baemo.domains.clubs.domain.policy.member;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveAppliedMemberPolicy {

    private final UserId userId;
    private final ClubId clubId;


    public static RetrieveAppliedMemberPolicy execute(UserId userId, ClubId clubId) {
        return new RetrieveAppliedMemberPolicy(userId, clubId);
    }

    public LoadStep valid(final BiFunction<UserId, ClubId, ClubMember> getUser) {
        ClubMember clubMember = getUser.apply(userId, clubId);
        if (!clubMember.isManagerRole()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return LoadStep.of(clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubId clubId;

        public QClubMemberDTO.Members load(final Function<ClubId, QClubMemberDTO.Members> getClub) {
            return getClub.apply(clubId);
        }
    }
}
