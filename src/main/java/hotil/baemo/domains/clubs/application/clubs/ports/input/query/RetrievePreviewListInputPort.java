package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrievePreviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrievePreviewListInputPort implements RetrievePreviewUseCase {
    private final QueryClubsOutputPort queryClubsOutputPort;
    @Override
    public PreviewResponse.ClubsPreviewList retrieveClubsPreviewList() {
        return queryClubsOutputPort.retrievePreviewList();
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrieveAllClubsPreviewList(Pageable pageable) {
        return queryClubsOutputPort.retrieveAllPreviewList(pageable);
    }
}
