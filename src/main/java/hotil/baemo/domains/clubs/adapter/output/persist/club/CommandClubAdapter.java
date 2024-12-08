package hotil.baemo.domains.clubs.adapter.output.persist.club;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.ClubsEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.club.mapper.ClubsMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.club.service.ClubsImageService;
import hotil.baemo.domains.clubs.application.ports.output.club.CommandClubOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.Club;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.ClubImage;
import hotil.baemo.domains.clubs.domain.value.club.ClubImageUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandClubAdapter implements CommandClubOutputPort {
    private final ClubsImageService clubsImageService;
    private final ClubsJpaRepository clubJpaRepository;

    @Override
    public ClubId saveClub(Club club, ClubImage clubImage) {
        if (clubImage.clubProfileImage() != null) {
            final var profileImagePath = clubsImageService.saveProfileImage(clubImage.clubProfileImage());
            club.updateClubProfileImageUrl(new ClubImageUrl(profileImagePath));
        }
        if (clubImage.clubBackGroundImage() != null) {
            final var backGroundImagePath = clubsImageService.saveBackGroundImage(clubImage.clubBackGroundImage());
            final var thumbnailImagePath = clubsImageService.saveThumbnailImage(clubImage.clubBackGroundImage());
            club.updateClubBackgroundImageUrl(new ClubImageUrl(backGroundImagePath));
            club.updateClubThumbnailImageUrl(new ClubImageUrl(thumbnailImagePath));
        }
        final var entity = ClubsMapper.convert(club);

        final var saved = clubJpaRepository.save(entity);
        final var clubsId = saved.getId();

        return new ClubId(clubsId);
    }

    @Override
    public Club loadClub(ClubId clubId) {
        ClubsEntity entity = clubJpaRepository.findById(clubId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
        return ClubsMapper.convert(entity);

    }

    @Override
    public void deleteClub(Club club) {
        final var clubs = clubJpaRepository.loadById(club.getClubId().clubsId());
        clubs.delete();
    }

    @Override
    public void deleteClub(ClubId clubId) {
        final var clubs = clubJpaRepository.loadById(clubId);
        clubs.delete();
    }
}