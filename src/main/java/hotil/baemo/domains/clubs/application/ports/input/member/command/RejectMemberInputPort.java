package hotil.baemo.domains.clubs.application.ports.input.member.command;

import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.usecases.member.command.RejectMemberUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.policy.member.RejectMemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectMemberInputPort implements RejectMemberUseCase {

    private final CommandClubMemberOutputPort commandClubMemberOutputPort;

    @Override
    public void rejectMember(UserId userId, ClubId clubId, UserId targetId) {
        RejectMemberPolicy.execute(userId, targetId, clubId)
            .valid(commandClubMemberOutputPort::loadClubUser)
            .load(commandClubMemberOutputPort::loadClubUser)
            .execute()
            .persist(commandClubMemberOutputPort::delete);
    }
}