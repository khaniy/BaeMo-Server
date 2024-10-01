package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveMyClubsUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RetrieveMyClubsInputPort implements RetrieveMyClubsUseCase {
    private final QueryClubsOutputPort queryClubsOutputPort;
    @Override
    public PreviewResponse.ClubsPreviewList retrieveMyClubsList(ClubsUserId clubsUserId) {
        return queryClubsOutputPort.retrievePreviewList(clubsUserId);
    }
}
