package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsJoinOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveJoinUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.clubs.specification.query.ClubsJoinQuerySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveJoinInputPort implements RetrieveJoinUseCase {
    private final ClubsUserRepository clubsUserRepository;
    private final QueryClubsJoinOutputPort queryClubsJoinOutputPort;

    @Override
    public JoinResponse.GetDTOList retrieveList(ClubsUserId clubsUserId, ClubsId clubsId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        ClubsJoinQuerySpecification.spec(clubsUser);
        return queryClubsJoinOutputPort.getJoinList(clubsId);
    }
}
