package hotil.baemo.domains.clubs.application.usecases.club.query;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import org.springframework.data.domain.Pageable;

public interface RetrieveClubListUseCase {

    QClubDTO.ClubPreviewList retrieveAllClubsPreviewList(Pageable pageable);

    QClubDTO.ClubPreviewList retrieveUserClubsList(UserId userId);

    QClubDTO.ClubPreviewList retrieveHomePreviewList();
}