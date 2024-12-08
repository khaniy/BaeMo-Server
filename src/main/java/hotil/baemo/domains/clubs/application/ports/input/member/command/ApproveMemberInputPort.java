package hotil.baemo.domains.clubs.application.ports.input.member.command;

import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.command.ApproveMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.member.ApproveMemberPolicy;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApproveMemberInputPort implements ApproveMemberUseCase {

    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final ClubMemberEventOutputPort clubMemberEventOutputPort;

    @Override
    public void approveMember(UserId userId, ClubId clubId, UserId targetId) {
        ApproveMemberPolicy.execute(userId, targetId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubMemberOutputPort::loadClubUser)
            .execute()
            .persist(commandClubMemberOutputPort::saveUser)
            .produce(clubMemberEventOutputPort::sendJoinUserEvent);
    }
}