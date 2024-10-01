package hotil.baemo.domains.clubs.application.replies.usecases.query;

import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;

public interface RetrieveRepliesUseCase {
    RetrieveRepliesDTO.RepliesDetailList retrieve(RepliesPostId repliesPostId);
}
