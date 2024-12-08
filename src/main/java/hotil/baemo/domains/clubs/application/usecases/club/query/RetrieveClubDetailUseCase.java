package hotil.baemo.domains.clubs.application.usecases.club.query;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface RetrieveClubDetailUseCase {
    QClubDTO.ClubDetailView retrieveClubDetail(UserId userId, ClubId clubId);
}
