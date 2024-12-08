package hotil.baemo.domains.clubs.adapter.output.persist.member;

import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubMemberQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.application.ports.output.member.QueryClubMemberOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubMemberAdapter implements QueryClubMemberOutputPort {

    private final ClubMemberQRepository clubMemberQRepository;

    @Override
    public QClubMemberDTO.Members retrieveMembers(ClubId clubId) {
        return clubMemberQRepository.retrieveMembers(clubId.clubsId());
    }

    @Override
    public QClubMemberDTO.Members retrieveAppliedMembers(ClubId clubId) {
        return clubMemberQRepository.retrieveAppliedMembers(clubId.clubsId());
    }
}