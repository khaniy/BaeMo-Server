package hotil.baemo.domains.clubs.application.clubs.usecases.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import org.springframework.data.domain.Pageable;

public interface RetrievePreviewUseCase {
    PreviewResponse.ClubsPreviewList retrieveClubsPreviewList();
    PreviewResponse.ClubsPreviewList retrieveAllClubsPreviewList(Pageable pageable);
}