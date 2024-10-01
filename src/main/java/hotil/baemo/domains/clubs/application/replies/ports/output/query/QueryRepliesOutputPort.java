package hotil.baemo.domains.clubs.application.replies.ports.output.query;

import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;

public interface QueryRepliesOutputPort {
    Replies load(RepliesId repliesId);

    RetrieveRepliesDTO.RepliesDetailList loadRepliesDetailList(RepliesPostId repliesPostId);

    ClubsUser loadClubsUser(RepliesPostId repliesPostId, RepliesWriter repliesWriter);

    ClubsUser loadClubsUser(RepliesId repliesId, RepliesWriter repliesWriter);
}
