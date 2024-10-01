package hotil.baemo.domains.clubs.adapter.replies.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.dsl.QueryDslReplies;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.mapper.RepliesEntityMapper;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.repository.RepliesEntityJpaRepository;
import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.application.replies.ports.output.query.QueryRepliesOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryRepliesOutputAdapter implements QueryRepliesOutputPort {
    private final QueryDslReplies queryDslReplies;
    private final RepliesEntityJpaRepository repliesEntityJpaRepository;
    private final RepliesEntityMapper repliesEntityMapper;

    @Override
    public Replies load(RepliesId repliesId) {
        final var entity = repliesEntityJpaRepository.findById(repliesId.id())
                .orElseThrow(() -> new CustomException(ResponseCode.REPLIES_NOT_FOUND));
        return repliesEntityMapper.convert(entity);
    }

    @Override
    public RetrieveRepliesDTO.RepliesDetailList loadRepliesDetailList(RepliesPostId repliesPostId) {
        return queryDslReplies.loadRepliesDetailList(repliesPostId);
    }

    @Override
    public ClubsUser loadClubsUser(RepliesPostId repliesPostId, RepliesWriter repliesWriter) {
        return queryDslReplies.loadClubsUser(repliesPostId, repliesWriter);
    }

    @Override
    public ClubsUser loadClubsUser(RepliesId repliesId, RepliesWriter repliesWriter) {
        return queryDslReplies.loadClubsUser(repliesId, repliesWriter);
    }
}