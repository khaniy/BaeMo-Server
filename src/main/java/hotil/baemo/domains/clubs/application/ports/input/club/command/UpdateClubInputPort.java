package hotil.baemo.domains.clubs.application.ports.input.club.command;

import hotil.baemo.domains.clubs.application.ports.output.club.CommandClubOutputPort;
import hotil.baemo.domains.clubs.application.usecases.club.command.UpdateClubUseCase;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.club.UpdateClubPolicy;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateClubInputPort implements UpdateClubUseCase {
    private final CommandClubOutputPort commandClubOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;

    @Override
    public void updateClubs(UserId userId, ClubId clubId, ClubName clubName, ClubSimpleDescription clubSimpleDescription, ClubDescription clubDescription, ClubLocation clubLocation, ClubImage clubImage) {
        UpdateClubPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubOutputPort::loadClub)
            .execute(clubName, clubSimpleDescription, clubDescription, clubLocation, clubImage)
            .persist(commandClubOutputPort::saveClub);
    }
}