package hotil.baemo.domains.clubs.application.clubs.ports.input.command;

import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandJoinOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.JoinClubsUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinClubInputPort implements JoinClubsUseCase {
    private final ClubsUserRepository clubsUserRepository;
    private final CommandJoinOutputPort joinOutputPort;

    @Override
    public void join(ClubsUserId clubsUserId, ClubsId clubsId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        final var request = clubsUser.executeJoin().joinRequest(clubsId);
        joinOutputPort.saveJoinRequest(request);
    }

    @Override
    public void handle(ClubsUserId clubsUserId, ClubsId clubsId, ClubsNonMember clubsNonMember, Boolean isAccept) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        final var result = clubsUser.executeHandle().handle(clubsNonMember, isAccept);
        joinOutputPort.saveJoinResult(result);
    }
}