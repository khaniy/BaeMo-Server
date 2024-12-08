package hotil.baemo.domains.clubs.application.ports.input.club.command;

import hotil.baemo.domains.clubs.application.ports.output.club.ClubEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.club.CommandClubOutputPort;
import hotil.baemo.domains.clubs.application.usecases.club.command.DeleteClubUseCase;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.club.DeleteClubPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteClubInputPort implements DeleteClubUseCase {

    private final CommandClubOutputPort commandClubOutputPort;
    private final ClubEventOutputPort clubEventOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;


    @Override
    public void deleteClubs(ClubId clubId, UserId userId) {
        DeleteClubPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubOutputPort::loadClub)
            .execute()
            .persist(commandClubOutputPort::deleteClub)
            .produce(clubEventOutputPort::sendDeletedEvent);
    }
}