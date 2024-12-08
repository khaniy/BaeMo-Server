package hotil.baemo.domains.clubs.application.ports.input.member.command;

import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.command.ApplyClubUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.member.ApplyClubPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyClubInputPort implements ApplyClubUseCase {

    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final ClubMemberEventOutputPort clubMemberEventOutputPort;

    @Override
    public void applyClub(UserId userId, ClubId clubId) {
        ApplyClubPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .execute()
            .persist(commandClubMemberOutputPort::saveUser)
            .produce(clubMemberEventOutputPort::sendAppliedEvent);
    }
}