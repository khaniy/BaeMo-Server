package hotil.baemo.domains.clubs.application.post.ports.input.command;

import hotil.baemo.domains.clubs.application.post.ports.output.command.CommandClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.ports.output.query.QueryClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.command.DeleteClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import hotil.baemo.domains.clubs.domain.post.specification.command.DeleteClubsPostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteClubsPostInputPort implements DeleteClubsPostUseCase {
    private final ClubsUserRepository clubsUserRepository;
    private final QueryClubsPostOutputPort queryClubsPostOutputPort;
    private final CommandClubsPostOutputPort commandClubsPostOutputPort;

    @Override
    public void delete(ClubsUserId clubsUserId, ClubsPostId clubsPostId, ClubsId clubsId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        final var clubsPost = queryClubsPostOutputPort.load(clubsPostId);
        DeleteClubsPostSpecification.spec(clubsUser, new ClubsPostWriter(clubsUserId.id()))
                .delete(clubsPost);

        commandClubsPostOutputPort.delete(clubsPost);
    }
}
