package hotil.baemo.domains.clubs.adapter.replies.output.persistence.mapper;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.RepliesEntity;
import hotil.baemo.domains.clubs.domain.replies.entity.*;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import org.springframework.stereotype.Service;

@Service
public class RepliesEntityMapper {

    public RepliesEntity convert(final Replies domain) {
        return RepliesEntity.builder()
                .repliesId(domain.getRepliesId() != null ? domain.getRepliesId().id() : null)
                .repliesPostId(domain.getRepliesPostId().id())
                .repliesWriter(domain.getRepliesWriter().id())
                .preRepliesId(domain.getPreRepliesId() != null ? domain.getPreRepliesId().id() : null)
                .repliesContent(domain.getRepliesContent().content())
                .build();
    }

    public Replies convert(final RepliesEntity entity) {
        return Replies.builder()
                .repliesId(new RepliesId(entity.getRepliesId()))
                .repliesPostId(new RepliesPostId(entity.getRepliesPostId()))
                .repliesWriter(new RepliesWriter(entity.getRepliesWriter()))
                .preRepliesId(new PreRepliesId(entity.getPreRepliesId()))
                .repliesContent(new RepliesContent(entity.getRepliesContent()))
                .build();
    }
}