package hotil.baemo.domains.clubs.application.clubs.usecases.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface RetrieveHomeUseCase {
    ClubsResponse.HomeDTO retrieve(ClubsUserId clubsUserId, ClubsId clubsId);
}
