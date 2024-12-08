package hotil.baemo.domains.clubs.application.ports.input.member.command;

import hotil.baemo.domains.clubs.application.ports.output.club.CommandClubOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.command.ExitClubUseCase;
import hotil.baemo.domains.clubs.application.usecases.member.command.ExpelMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.member.ExitClubPolicy;
import hotil.baemo.domains.clubs.domain.policy.member.ExpelMemberPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpelMemberInputPort implements ExpelMemberUseCase, ExitClubUseCase {

    private final ClubMemberEventOutputPort clubMemberEventOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final CommandClubOutputPort commandClubOutputPort;


    @Override
    public void exitClub(ClubId clubId, UserId userId) {
        ExitClubPolicy.execute(userId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubMemberOutputPort::loadDelegateUser)
            .execute()
            .persist(
                commandClubMemberOutputPort::delete,
                commandClubMemberOutputPort::saveUser,
                commandClubOutputPort::deleteClub
            )
            .produce(
                clubMemberEventOutputPort::sendExitUserEvent,
                clubMemberEventOutputPort::sendUpdateUserRoleEvent
            );

    }

    @Override
    public void expelMember(ClubId clubId, UserId actorId, UserId targetId) {
        ExpelMemberPolicy.execute(actorId, targetId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubMemberOutputPort::loadClubUser)
            .execute()
            .persist(commandClubMemberOutputPort::delete)
            .produce(clubMemberEventOutputPort::sendExpelUserEvent);
    }


    @Override
    public void exitAllClub(UserId userId) {
        commandClubMemberOutputPort.deleteAllByUsersId(userId);
    }
}