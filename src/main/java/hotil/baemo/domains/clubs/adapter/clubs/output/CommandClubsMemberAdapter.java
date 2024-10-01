package hotil.baemo.domains.clubs.adapter.clubs.output;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandClubsMemberOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandClubsMemberAdapter implements CommandClubsMemberOutputPort {
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;
    @Override
    public void updateRole(ClubsId clubsId, ClubsUserId clubsUserId, ClubsRole clubsRole) {
        final var clubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(clubsUserId, clubsId);
        clubsMemberEntity.update(clubsRole);
    }
}
