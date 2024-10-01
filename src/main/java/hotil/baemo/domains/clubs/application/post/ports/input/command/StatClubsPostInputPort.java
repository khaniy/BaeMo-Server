package hotil.baemo.domains.clubs.application.post.ports.input.command;

import hotil.baemo.domains.clubs.application.post.ports.output.command.StatClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.command.StatClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatClubsPostInputPort implements StatClubsPostUseCase {
    private final StatClubsPostOutputPort statClubsPostOutputPort;

    @Override
    public void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId) {
        statClubsPostOutputPort.incrementViewCount(clubsPostId, clubsUserId);
    }
}