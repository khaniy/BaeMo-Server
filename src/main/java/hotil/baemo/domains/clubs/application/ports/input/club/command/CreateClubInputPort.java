package hotil.baemo.domains.clubs.application.ports.input.club.command;

import hotil.baemo.domains.clubs.application.ports.output.club.ClubEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.club.CommandClubOutputPort;
import hotil.baemo.domains.clubs.application.usecases.club.command.CreateClubUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.policy.club.CreateClubPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateClubInputPort implements CreateClubUseCase {
    private final CommandClubOutputPort commandClubOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final ClubEventOutputPort clubEventOutputPort;

    @Override
    public ClubId createClubs(
        UserId userId, ClubName clubName,
        ClubSimpleDescription clubSimpleDescription,
        ClubDescription clubDescription, ClubLocation clubLocation,
        ClubImage clubImage
    ) {
        return CreateClubPolicy.execute(userId)
            .create(clubName, clubSimpleDescription, clubDescription, clubLocation, clubImage)
            .persist(
                commandClubOutputPort::saveClub,
                commandClubMemberOutputPort::saveUser
            )
            .produce(clubEventOutputPort::sendCreatedEvent);
    }
}