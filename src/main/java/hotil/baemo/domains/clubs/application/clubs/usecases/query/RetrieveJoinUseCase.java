package hotil.baemo.domains.clubs.application.clubs.usecases.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;

public interface RetrieveJoinUseCase {
    JoinResponse.GetDTOList retrieveList(ClubsUserId clubsUserId, ClubsId clubsId);
}
