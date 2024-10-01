package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl;

import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;

public interface ClubsJoinQueryDsl {
    JoinResponse.GetDTOList getList(ClubsId clubsId);
}
