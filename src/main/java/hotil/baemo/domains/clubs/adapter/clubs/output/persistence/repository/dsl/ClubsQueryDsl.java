package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl;

import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import org.springframework.data.domain.Pageable;

public interface ClubsQueryDsl {
    ClubsResponse.HomeDTO retrieveHomeDTO(ClubsId clubsId, ClubsUser clubsUser);

    ClubsResponse.MembersDTO retrieveMembersDTO(ClubsId clubsId);

    PreviewResponse.ClubsPreviewList retrievePreviewList();

    PreviewResponse.ClubsPreviewList retrievePreviewList(ClubsUserId clubsUserId);

    PreviewResponse.ClubsPreviewList retrieveAllPreviewList(Pageable pageable);
}
