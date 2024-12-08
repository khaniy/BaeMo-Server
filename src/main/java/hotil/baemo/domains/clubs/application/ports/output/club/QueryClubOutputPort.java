package hotil.baemo.domains.clubs.application.ports.output.club;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import org.springframework.data.domain.Pageable;

public interface QueryClubOutputPort {

    QClubDTO.ClubDetailView retrieveClubDetail(ClubId clubId, ClubMember clubMember);

    QClubDTO.ClubPreviewList retrievePreviewList(UserId userId);

    QClubDTO.ClubPreviewList retrieveAllPreviewList(Pageable pageable);

    QClubDTO.ClubPreviewList retrieveHomePreviewList();
}