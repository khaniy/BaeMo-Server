package hotil.baemo.domains.clubs.application.clubs.ports.input.operation;

import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.operation.ExitClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsMemberOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.operation.ExitUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.clubs.policy.operation.ExitPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExitInputPort implements ExitUseCase {

    private final ExitClubsOutputPort exitClubsOutputPort;
    private final ClubsUserRepository clubsUserRepository;
    private final CommandClubsOutputPort commandClubsOutputPort;
    private final QueryClubsMemberOutputPort queryClubsMemberOutputPort;

    @Override
    public void exitClubs(ClubsId clubsId, ClubsUserId clubsUserId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);

        ExitPolicy
            .execute(clubsUser)
            .loadDelegableUser(() -> queryClubsMemberOutputPort.loadDelegableUser(clubsId))
            .delegate((delegableUserId) -> exitClubsOutputPort.delegate(clubsId, delegableUserId))
            .delete(() -> commandClubsOutputPort.deleteClubs(clubsId))
            .exit(() -> exitClubsOutputPort.exit(clubsUser));
    }
}