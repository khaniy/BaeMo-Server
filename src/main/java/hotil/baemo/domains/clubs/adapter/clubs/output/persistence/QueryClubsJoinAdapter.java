package hotil.baemo.domains.clubs.adapter.clubs.output.persistence;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsJoinRequestJpaRepository;
import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsJoinOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubsJoinAdapter implements QueryClubsJoinOutputPort {
    private final ClubsJoinRequestJpaRepository clubsJoinRequestJpaRepository;

    @Override
    public JoinResponse.GetDTOList getJoinList(ClubsId clubsId) {
        return clubsJoinRequestJpaRepository.getList(clubsId);
    }
}
