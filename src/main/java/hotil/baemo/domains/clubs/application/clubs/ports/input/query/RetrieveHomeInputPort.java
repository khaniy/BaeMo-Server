package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.ClubsRepositoryOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveHomeUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.specification.query.ClubsHomeRetrieveSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveHomeInputPort implements RetrieveHomeUseCase {
    private final QueryClubsOutputPort queryClubsOutputPort;
    private final ClubsRepositoryOutputPort clubsRepositoryOutputPort;

    @Override
    public ClubsResponse.HomeDTO retrieve(ClubsUserId clubsUserId, ClubsId clubsId) {
        final var clubsUser = clubsRepositoryOutputPort.loadClubsUser(clubsUserId, clubsId);
        ClubsHomeRetrieveSpecification.homeSpec(clubsUser);

        return queryClubsOutputPort.retrieveHome(clubsId, clubsUser);
    }
}
