package hotil.baemo.domains.clubs.application.ports.input.member.query;

import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.QueryClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.query.RetrieveClubMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.member.RetrieveAppliedMemberPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveClubMemberInputPort implements RetrieveClubMemberUseCase {

    private final QueryClubMemberOutputPort queryClubMemberOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;

    @Override
    public QClubMemberDTO.Members retrieveClubMembers(ClubId clubId, UserId userId) {
        return queryClubMemberOutputPort.retrieveMembers(clubId);
    }

    @Override
    public QClubMemberDTO.Members retrieveAppliedMembers(UserId userId, ClubId clubId) {
        return RetrieveAppliedMemberPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(queryClubMemberOutputPort::retrieveAppliedMembers);
    }
}