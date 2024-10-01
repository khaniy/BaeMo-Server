package hotil.baemo.domains.clubs.application.clubs.usecases.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsDescription;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsLocation;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsName;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsSimpleDescription;
import org.springframework.web.multipart.MultipartFile;

public interface CreateClubsUseCase {
    ClubsId createClubs(
            ClubsUserId clubsUserId, ClubsName clubsName,
            ClubsSimpleDescription clubsSimpleDescription,
            ClubsDescription clubsDescription, ClubsLocation clubsLocation,
            MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage
    );
}
