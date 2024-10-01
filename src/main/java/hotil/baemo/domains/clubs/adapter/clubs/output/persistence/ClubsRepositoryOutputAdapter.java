package hotil.baemo.domains.clubs.adapter.clubs.output.persistence;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper.ClubsMemberMapper;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.application.clubs.ports.output.ClubsRepositoryOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubsRepositoryOutputAdapter implements ClubsRepositoryOutputPort {
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;

    @Override
    public void saveClubsUser(ClubsUser clubsUser) {
        clubsMemberJpaRepository.save(ClubsMemberEntity.builder()
                .clubsId(clubsUser.getClubsId().clubsId())
                .usersId(clubsUser.getClubsUserId().id())
                .clubRole(clubsUser.getRole())
                .build());
    }

    @Override
    public ClubsUser loadClubsUser(ClubsUserId clubsUserId, ClubsId clubsId) {
        final Long clubsUserIdx = clubsUserId.id();
        final Long clubsIdx = clubsId.clubsId();
        final var entity = clubsMemberJpaRepository.findByUsersIdAndClubsId(clubsUserIdx, clubsIdx)
                .orElse(ClubsMemberEntity.builder()
                        .clubsId(clubsIdx)
                        .clubRole(ClubsRole.NON_MEMBER)
                        .usersId(clubsUserIdx)
                        .build());

        return ClubsMemberMapper.convert(entity);
    }
}