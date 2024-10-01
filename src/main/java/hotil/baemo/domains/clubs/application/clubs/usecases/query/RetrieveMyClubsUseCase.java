package hotil.baemo.domains.clubs.application.clubs.usecases.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface RetrieveMyClubsUseCase {
    PreviewResponse.ClubsPreviewList retrieveMyClubsList(ClubsUserId clubsUserId);
}