package hotil.baemo.domains.clubs.application.ports.input.club.query;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.application.ports.output.club.QueryClubOutputPort;
import hotil.baemo.domains.clubs.application.usecases.club.query.RetrieveClubDetailUseCase;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.club.RetrieveClubDetailPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveClubDetailInputPort implements RetrieveClubDetailUseCase {

    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final QueryClubOutputPort queryClubOutputPort;

    @Override
    public QClubDTO.ClubDetailView retrieveClubDetail(UserId userId, ClubId clubId) {
        return RetrieveClubDetailPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(queryClubOutputPort::retrieveClubDetail);
    }
}
