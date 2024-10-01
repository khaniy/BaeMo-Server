package hotil.baemo.domains.clubs.application.clubs.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateClubsUseCase {
    void updateClubs(ClubsUserId clubsUserId, ClubsId clubsId, Clubs newClubs, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage);
}
