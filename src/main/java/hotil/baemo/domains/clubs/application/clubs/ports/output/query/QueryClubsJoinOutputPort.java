package hotil.baemo.domains.clubs.application.clubs.ports.output.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;

public interface QueryClubsJoinOutputPort {
    JoinResponse.GetDTOList getJoinList(ClubsId clubsId);
}
