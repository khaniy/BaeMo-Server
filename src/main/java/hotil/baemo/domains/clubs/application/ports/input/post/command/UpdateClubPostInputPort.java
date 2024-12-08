package hotil.baemo.domains.clubs.application.ports.input.post.command;

import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.post.command.UpdateClubPostUseCase;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.policy.post.UpdateClubPostPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateClubPostInputPort implements UpdateClubPostUseCase {

    private final CommandClubPostOutputPort commandClubPostOutputPort;

    @Override
    public void update(ClubPostId clubPostId, UserId userId, ClubPostVOGroup clubPostVOGroup) {
        UpdateClubPostPolicy.execute(userId, clubPostId)
            .valid(commandClubPostOutputPort::loadClubPost)
            .update(clubPostVOGroup)
            .save(commandClubPostOutputPort::save);
    }
}
