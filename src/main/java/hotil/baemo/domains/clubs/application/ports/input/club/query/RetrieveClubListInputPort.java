package hotil.baemo.domains.clubs.application.ports.input.club.query;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.application.ports.output.club.QueryClubOutputPort;
import hotil.baemo.domains.clubs.application.usecases.club.query.RetrieveClubListUseCase;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveClubListInputPort implements RetrieveClubListUseCase {
    private final QueryClubOutputPort queryClubOutputPort;

    @Override
    public QClubDTO.ClubPreviewList retrieveAllClubsPreviewList(Pageable pageable) {
        return queryClubOutputPort.retrieveAllPreviewList(pageable);
    }

    @Override
    public QClubDTO.ClubPreviewList retrieveUserClubsList(UserId userId) {
        return queryClubOutputPort.retrievePreviewList(userId);
    }

    @Override
    public QClubDTO.ClubPreviewList retrieveHomePreviewList() {
        return queryClubOutputPort.retrieveHomePreviewList();
    }
}
