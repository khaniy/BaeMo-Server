package hotil.baemo.domains.clubs.application.replies.ports.input.query;

import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.application.replies.ports.output.query.QueryRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.usecases.query.RetrieveRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RetrieveRepliesInputPort implements RetrieveRepliesUseCase {
    private final QueryRepliesOutputPort queryRepliesOutputPort;

    @Override
    public RetrieveRepliesDTO.RepliesDetailList retrieve(RepliesPostId repliesPostId) {
        return queryRepliesOutputPort.loadRepliesDetailList(repliesPostId);
    }
}