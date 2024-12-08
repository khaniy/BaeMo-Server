package hotil.baemo.domains.clubs.application.ports.output.member;

import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;

public interface QueryClubMemberOutputPort {

    QClubMemberDTO.Members retrieveMembers(ClubId clubId);

    QClubMemberDTO.Members retrieveAppliedMembers(ClubId clubId);
}