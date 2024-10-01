package hotil.baemo.domains.clubs.adapter.clubs.output;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.operation.ExitClubsOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExitClubsAdapter implements ExitClubsOutputPort {
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;
    private final CommandClubsEventOutputPort commandClubsEventOutputPort;

    @Override
    public void exit(ClubsUser clubsUser) {
        final var clubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(clubsUser.getClubsUserId(), clubsUser.getClubsId());
        clubsMemberEntity.delete();
        commandClubsEventOutputPort.sendDeletedEvent(new ClubsId(clubsMemberEntity.getClubsId()));
    }

    @Override
    public void delegate(ClubsId clubsId, ClubsUserId clubsUserId) {
        final var clubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(clubsUserId.id(), clubsId.clubsId());
        clubsMemberEntity.delegate();
        commandClubsEventOutputPort.sendUpdateUserRoleEvent(
            new ClubsId(clubsMemberEntity.getClubsId()),
            new ClubsUserId(clubsMemberEntity.getUsersId()),
            clubsMemberEntity.getClubRole()
        );
    }
}