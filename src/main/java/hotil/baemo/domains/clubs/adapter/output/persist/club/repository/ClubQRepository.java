package hotil.baemo.domains.clubs.adapter.output.persist.club.repository;

import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import org.springframework.data.domain.Pageable;

public interface ClubQRepository {

    QClubDTO.ClubPreviewList retrievePreviewList(UserId userId);

    QClubDTO.ClubPreviewList retrieveAllPreviewList(Pageable pageable);

    QClubDTO.ClubDetailView retrieveClubDetail(ClubId clubId, ClubMember clubMember);

    QClubDTO.ClubPreviewList retrieveHomePreviewList();
}
