package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveUserProfileUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveClubUserProfileInputPort implements RetrieveUserProfileUseCase {

    private final QueryClubsOutputPort queryClubsOutputPort;

    @Override
    public PreviewResponse.ClubsPreviewList retrieve(ClubsUserId clubsUserId) {
        return queryClubsOutputPort.retrievePreviewList(clubsUserId);
    }
}
