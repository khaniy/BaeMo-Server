package hotil.baemo.domains.clubs.adapter.clubs.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper.ClubsMapper;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubsOutputAdapter implements QueryClubsOutputPort {
    private final ClubsJpaRepository clubsJpaRepository;
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;

    @Override
    public Clubs load(ClubsId clubsId) {
        final var clubsEntity = clubsJpaRepository.findById(clubsId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
        final var clubsMemberEntity = clubsMemberJpaRepository.findByClubIdAndClubRole(clubsEntity.getId(), ClubsRole.ADMIN)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
        return ClubsMapper.convert(clubsEntity, clubsMemberEntity);
    }

    @Override
    public ClubsResponse.HomeDTO retrieveHome(ClubsId clubsId, ClubsUser clubsUser) {
        return clubsJpaRepository.retrieveHomeDTO(clubsId, clubsUser);
    }

    @Override
    public ClubsResponse.MembersDTO retrieveMembers(ClubsId clubsId) {
        return clubsJpaRepository.retrieveMembersDTO(clubsId);
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrievePreviewList() {
        return clubsJpaRepository.retrievePreviewList();
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrieveAllPreviewList(Pageable pageable) {
        return clubsJpaRepository.retrieveAllPreviewList(pageable);
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrievePreviewList(ClubsUserId clubsUserId) {
        return clubsJpaRepository.retrievePreviewList(clubsUserId);
    }
}
