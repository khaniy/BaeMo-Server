package hotil.baemo.domains.clubs.adapter.output.persist.member.repository;

import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;

public interface ClubMemberQRepository {
    QClubMemberDTO.Members retrieveAppliedMembers(Long clubId);

    QClubMemberDTO.Members retrieveMembers(Long clubId);
}
