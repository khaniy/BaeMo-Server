package hotil.baemo.domains.clubs.application.ports.input.member.command;

import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.command.ChangeRoleUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.policy.member.ChangeMemberRolePolicy;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeRoleInputPort implements ChangeRoleUseCase {
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final ClubMemberEventOutputPort clubMemberEventOutputPort;

    @Override
    public void change(ClubId clubId, UserId actorId, UserId targetId, ClubRole clubRole) {
        ChangeMemberRolePolicy.execute(actorId, targetId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubMemberOutputPort::loadClubUser)
            .execute(clubRole)
            .persist(commandClubMemberOutputPort::saveUser)
            .produce(clubMemberEventOutputPort::sendUpdateUserRoleEvent);
    }
}