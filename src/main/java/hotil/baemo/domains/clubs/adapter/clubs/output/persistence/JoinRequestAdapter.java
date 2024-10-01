package hotil.baemo.domains.clubs.adapter.clubs.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper.JoinRequestEntityMapper;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsJoinRequestJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.ports.output.ClubsRepositoryOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.command.CommandJoinOutputPort;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsMember;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsName;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinRequestAdapter implements CommandJoinOutputPort {
    private final ClubsJoinRequestJpaRepository joinRequestJpaRepository;
    private final ClubsRepositoryOutputPort clubsRepositoryOutputPort;
    private final CommandClubsEventOutputPort commandClubsEventOutputPort;

    @Override
    public void saveJoinRequest(Join.Request joinRequest) {
        final var entity = JoinRequestEntityMapper.convert(joinRequest);
        joinRequestJpaRepository.save(entity);
        commandClubsEventOutputPort.sendJoinRequestEvent(
            new ClubsId(entity.getClubsId()),
            joinRequest.clubsNonMember().getClubsUserId()
        );
    }

    @Override
    public void saveJoinResult(Join.Result joinResult) {
        final var clubsNonMember = joinResult.clubsNonMember();
        final var isAccept = joinResult.isAccept();
        final var clubsId = clubsNonMember.getClubsId();
        final var clubsUserId = clubsNonMember.getClubsUserId();

        if (!joinRequestJpaRepository.existsByClubsIdAndNonMemberId(clubsId.clubsId(), clubsUserId.id())) {
            throw new CustomException(ResponseCode.CLUBS_NOT_FOUND);
        }

        joinRequestJpaRepository.deleteAllByClubsIdAndNonMemberId(clubsId.clubsId(), clubsUserId.id());

        if (isAccept) {
            clubsRepositoryOutputPort.saveClubsUser(ClubsMember.builder()
                .clubsUserId(clubsUserId)
                .clubsId(clubsId)
                .build());
            commandClubsEventOutputPort.sendJoinUserEvent(clubsUserId, clubsId);
        }
    }
}