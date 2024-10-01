package hotil.baemo.domains.clubs.application.replies.ports.input.command;

import hotil.baemo.domains.clubs.application.replies.ports.output.command.StatRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.usecases.command.LikeRepliesUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeRepliesInputPort implements LikeRepliesUseCase {
    private final StatRepliesOutputPort statRepliesOutputPort;

    @Override
    public void like(RepliesId repliesId, ClubsUserId clubsUserId) {
        statRepliesOutputPort.executeLike(repliesId, clubsUserId);
    }
}