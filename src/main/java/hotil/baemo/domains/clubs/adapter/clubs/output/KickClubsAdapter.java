package hotil.baemo.domains.clubs.adapter.clubs.output;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.ports.output.operation.KickClubsOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KickClubsAdapter implements KickClubsOutputPort {
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;

    @Override
    public void kick(ClubsUser target) {
        final var clubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(target.getClubsUserId(), target.getClubsId());
        clubsMemberEntity.delete();
    }
}
