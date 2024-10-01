package hotil.baemo.domains.clubs.application.post.ports.input.command;

import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsMemberOutputPort;
import hotil.baemo.domains.clubs.application.post.ports.output.command.CommandClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.command.CreateClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.policy.cretae.CreateClubsPostPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateClubsPostInputPort implements CreateClubsPostUseCase {
    private final CommandClubsPostOutputPort commandClubsPostOutputPort;
    private final QueryClubsMemberOutputPort queryClubsMemberOutputPort;

    @Override
    public ClubsPostId create(CreateClubsPostAggregate createClubsPostAggregate) {
        return CreateClubsPostPolicy.execute(createClubsPostAggregate)
            .validRole(queryClubsMemberOutputPort::loadClubsRole)
            .create(commandClubsPostOutputPort::save);
    }
}