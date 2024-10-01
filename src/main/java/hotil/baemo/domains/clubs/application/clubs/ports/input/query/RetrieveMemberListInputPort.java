package hotil.baemo.domains.clubs.application.clubs.ports.input.query;

import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsOutputPort;
import hotil.baemo.domains.clubs.application.clubs.usecases.query.RetrieveMemberListUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.clubs.specification.query.ClubsMembersRetrieveSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveMemberListInputPort implements RetrieveMemberListUseCase {
    private final ClubsUserRepository clubsUserRepository;
    private final QueryClubsOutputPort queryClubsOutputPort;

    @Override
    public ClubsResponse.MembersDTO retrieve(ClubsId clubsId, ClubsUserId clubsUserId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        ClubsMembersRetrieveSpecification.membersSpec(clubsUser);
        return queryClubsOutputPort.retrieveMembers(clubsId);
    }
}