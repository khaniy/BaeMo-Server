package hotil.baemo.domains.clubs.adapter.clubs.output.persistence;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper.ClubsMapper;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.service.ClubsImageService;
import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandClubsOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsAdmin;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsBackgroundImage;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsProfileImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommandClubsAdapter implements CommandClubsOutputPort {
    private final ClubsImageService clubsImageService;
    private final ClubsJpaRepository clubJpaRepository;
    private final ClubsUserRepository clubsUserRepository;

    @Override
    public ClubsId saveClubs(Clubs clubs, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage) {
        final var profileImagePath = clubsImageService.saveProfileImage(clubsProfileImage);
        final var backGroundImagePath = clubsImageService.saveBackGroundImage(clubsBackgroundImage);
        clubs.updateClubsProfileImage(new ClubsProfileImage(profileImagePath));
        clubs.updateClubsBackgroundImage(new ClubsBackgroundImage(backGroundImagePath));
        final var entity = ClubsMapper.convert(clubs);

        final var saved = clubJpaRepository.save(entity);
        final var clubsId = saved.getId();
        saveClubsUser(clubs, clubsId);

        return new ClubsId(clubsId);
    }

    @Override
    public void updateClubs(Clubs clubs, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage) {
        if (clubsProfileImage != null) {
            final var profileImagePath = clubsImageService.saveProfileImage(clubsProfileImage);
            clubs.updateClubsProfileImage(new ClubsProfileImage(profileImagePath));
        }

        if (clubsBackgroundImage != null) {
            final var backGroundImagePath = clubsImageService.saveBackGroundImage(clubsBackgroundImage);
            clubs.updateClubsBackgroundImage(new ClubsBackgroundImage(backGroundImagePath));
        }

        final var entity = ClubsMapper.convert(clubs);

        clubJpaRepository.save(entity);
    }

    @Override
    public void deleteClubs(Clubs clubs) {
        final var clubEntity = ClubsMapper.convert(clubs);
        clubJpaRepository.save(clubEntity);
    }

    @Override
    public void deleteClubs(ClubsId clubsId) {
        final var clubs = clubJpaRepository.loadById(clubsId);
        clubs.delete();
    }

    private void saveClubsUser(Clubs club, Long clubsId) {
        clubsUserRepository.saveClubsUser(ClubsAdmin.builder()
            .clubsUserId(new ClubsUserId(club.getClubsAdmin().getClubsUserId().id()))
            .clubsId(new ClubsId(clubsId))
            .build());
    }
}