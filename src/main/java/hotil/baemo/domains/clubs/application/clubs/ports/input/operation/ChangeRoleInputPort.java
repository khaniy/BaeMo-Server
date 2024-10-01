package hotil.baemo.domains.clubs.application.clubs.ports.input.operation;

import hotil.baemo.domains.clubs.application.clubs.ports.output.ClubsRepositoryOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandClubsMemberOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.operation.ChangeRoleUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.specification.operation.ChangeRoleSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeRoleInputPort implements ChangeRoleUseCase {
    private final ClubsRepositoryOutputPort clubsRepositoryOutputPort;
    private final CommandClubsMemberOutputPort commandClubsMemberOutputPort;
    private final CommandClubsEventOutputPort commandClubsEventOutputPort;

    @Override
    public void change(ClubsId clubsId, ClubsUserId actorId, ClubsUserId targetId, ClubsRole clubsRole) {
        final var clubsUser = clubsRepositoryOutputPort.loadClubsUser(actorId, clubsId);
        final var targetUser = clubsRepositoryOutputPort.loadClubsUser(targetId, clubsId);

        ChangeRoleSpecification.spec(clubsUser.getRole())
            .validBeforeAndAfter(targetUser.getRole(), clubsRole);

        commandClubsMemberOutputPort.updateRole(clubsId, targetUser.getClubsUserId(), clubsRole);
        commandClubsEventOutputPort.sendUpdateUserRoleEvent(clubsId, targetUser.getClubsUserId(), clubsRole);
    }
}