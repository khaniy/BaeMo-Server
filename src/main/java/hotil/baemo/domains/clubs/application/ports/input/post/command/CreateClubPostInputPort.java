package hotil.baemo.domains.clubs.application.ports.input.post.command;

import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.post.command.CreateClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.policy.post.CreateClubPostPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateClubPostInputPort implements CreateClubPostUseCase {

    private final CommandClubPostOutputPort commandClubPostOutputPort;
    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final CommandClubPostEventOutputPort commandClubPostEventOutputPort;

    @Override
    public ClubPostId create(ClubId clubId, UserId userId, ClubPostVOGroup clubPostVOGroup) {
        return CreateClubPostPolicy.execute(userId,clubId)
            .create(clubPostVOGroup)
            .validRole(commandClubMemberOutputPort::loadClubUser)
            .save(commandClubPostOutputPort::save)
            .produce(commandClubPostEventOutputPort::sendCreatedEvent);
    }
}