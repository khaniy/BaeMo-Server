package hotil.baemo.domains.clubs.adapter.post.output.persistence;

import hotil.baemo.domains.clubs.application.post.ports.output.command.StatClubsPostOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatClubsPostOutputAdapter implements StatClubsPostOutputPort {

    @Override
    public void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId) {
        // TODO : event
    }
}
