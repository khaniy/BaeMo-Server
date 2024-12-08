package hotil.baemo.domains.clubs.adapter.output.persist.club;

import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.application.ports.output.club.QueryClubOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubOutputAdapter implements QueryClubOutputPort {
    private final ClubQRepository clubQRepository;

    @Override
    public QClubDTO.ClubDetailView retrieveClubDetail(ClubId clubId, ClubMember clubMember) {
        return clubQRepository.retrieveClubDetail(clubId, clubMember);
    }

    @Override
    public QClubDTO.ClubPreviewList retrieveAllPreviewList(Pageable pageable) {
        return clubQRepository.retrieveAllPreviewList(pageable);
    }

    @Override
    public QClubDTO.ClubPreviewList retrievePreviewList(UserId userId) {
        return clubQRepository.retrievePreviewList(userId);
    }

    @Override
    public QClubDTO.ClubPreviewList retrieveHomePreviewList() {
        return clubQRepository.retrieveHomePreviewList();
    }
}
