package hotil.baemo.domains.clubs.application.usecases.member.query;

import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface RetrieveClubMemberUseCase {

    QClubMemberDTO.Members retrieveClubMembers(ClubId clubId, UserId userId);

    QClubMemberDTO.Members retrieveAppliedMembers(UserId userId, ClubId clubId);

}
