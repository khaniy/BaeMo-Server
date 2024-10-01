package hotil.baemo.domains.clubs.application.clubs.ports.output.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import org.springframework.data.domain.Pageable;

public interface QueryClubsOutputPort {
    Clubs load(ClubsId clubsId);

    ClubsResponse.HomeDTO retrieveHome(ClubsId clubsId, ClubsUser clubsUser);

    ClubsResponse.MembersDTO retrieveMembers(ClubsId clubsId);

    PreviewResponse.ClubsPreviewList retrievePreviewList();

    PreviewResponse.ClubsPreviewList retrievePreviewList(ClubsUserId clubsUserId);

    PreviewResponse.ClubsPreviewList retrieveAllPreviewList(Pageable pageable);
}