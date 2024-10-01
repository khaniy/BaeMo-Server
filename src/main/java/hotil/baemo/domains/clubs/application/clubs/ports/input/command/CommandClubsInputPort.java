package hotil.baemo.domains.clubs.application.clubs.ports.input.command;

import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.CreateClubsUseCase;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.DeleteClubsUseCase;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.UpdateClubsUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsAdmin;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsDescription;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsLocation;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsName;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsSimpleDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandClubsInputPort implements CreateClubsUseCase, UpdateClubsUseCase, DeleteClubsUseCase {
    private final CommandClubsOutputPort commandClubsOutputPort;
    private final QueryClubsOutputPort queryClubsOutputPort;
    private final ClubsUserRepository clubsUserRepository;
    private final CommandClubsEventOutputPort commandClubsEventOutputPort;

    @Override
    public ClubsId createClubs(
        ClubsUserId clubsUserId, ClubsName clubsName,
        ClubsSimpleDescription clubsSimpleDescription,
        ClubsDescription clubsDescription, ClubsLocation clubsLocation,
        MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage
    ) {

        final var clubsId = commandClubsOutputPort.saveClubs(Clubs.builder()
                .clubsAdmin(ClubsAdmin.builder().clubsUserId(clubsUserId).build())
                .clubsName(clubsName)
                .clubsSimpleDescription(clubsSimpleDescription)
                .clubsDescription(clubsDescription)
                .clubsLocation(clubsLocation)
                .build(),
            clubsProfileImage,
            clubsBackgroundImage
        );

        commandClubsEventOutputPort.sendCreatedEvent(clubsUserId, clubsId);

        return clubsId;
    }

    @Override
    public void updateClubs(ClubsUserId clubsUserId, ClubsId clubsId, Clubs newClubs, MultipartFile clubsProfileImage, MultipartFile clubsBackgroundImage) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        final var oldClubs = queryClubsOutputPort.load(clubsId);
        final var updated = clubsUser.updateClubs().update(oldClubs, newClubs);
        commandClubsOutputPort.updateClubs(updated, clubsProfileImage, clubsBackgroundImage);
    }

    @Override
    public void deleteClubs(ClubsId clubsId, ClubsUserId ClubsUserId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(ClubsUserId, clubsId);
        final var clubs = queryClubsOutputPort.load(clubsId);
        final var deleted = clubsUser.deleteClubs().delete(clubs);
        commandClubsOutputPort.deleteClubs(deleted);
    }
}