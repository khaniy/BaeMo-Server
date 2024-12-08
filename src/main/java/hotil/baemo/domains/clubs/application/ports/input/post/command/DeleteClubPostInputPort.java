package hotil.baemo.domains.clubs.application.ports.input.post.command;

import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.post.command.DeleteClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.policy.post.DeleteClubPostPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteClubPostInputPort implements DeleteClubPostUseCase {
    private final CommandClubPostOutputPort commandClubPostOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;

    @Override
    public void delete(UserId userId, ClubPostId clubPostId, ClubId clubId) {
        DeleteClubPostPolicy.execute(userId, clubId, clubPostId)
            .load(commandClubPostOutputPort::loadClubPost)
            .validRole(commandClubMemberOutputPort::loadClubUser)
            .delete(commandClubPostOutputPort::delete);
    }
}
