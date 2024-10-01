package hotil.baemo.domains.clubs.application.clubs.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import org.springframework.web.multipart.MultipartFile;

public interface CommandClubsOutputPort {
    ClubsId saveClubs(Clubs club, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage);

    void updateClubs(Clubs club, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage);

    void deleteClubs(Clubs clubs);
    void deleteClubs(ClubsId clubsId);
}
