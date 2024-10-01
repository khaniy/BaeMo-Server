package hotil.baemo.domains.clubs.application.clubs.ports.input.operation;

import hotil.baemo.domains.clubs.application.clubs.ports.output.ClubsRepositoryOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.operation.KickClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.operation.KickUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.specification.operation.KickSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class KickInputPort implements KickUseCase {
    private final ClubsRepositoryOutputPort clubsRepositoryOutputPort;
    private final KickClubsOutputPort kickClubsOutputPort;
    private final CommandClubsEventOutputPort commandClubsEventOutputPort;

    @Override
    public void kick(ClubsId clubsId, ClubsUserId actorId, ClubsUserId targetId) {
        final var actor = clubsRepositoryOutputPort.loadClubsUser(actorId, clubsId);
        final var target = clubsRepositoryOutputPort.loadClubsUser(targetId, clubsId);

        KickSpecification.spec(actor)
            .validRole(actor.getRole(), target.getRole());

        kickClubsOutputPort.kick(target);
        commandClubsEventOutputPort.sendKickUserEvent(targetId, clubsId);
    }
}