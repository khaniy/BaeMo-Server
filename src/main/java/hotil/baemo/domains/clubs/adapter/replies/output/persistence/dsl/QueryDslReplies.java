package hotil.baemo.domains.clubs.adapter.replies.output.persistence.dsl;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.dto.RepliesUserDTO;
import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;

public interface QueryDslReplies {
    ClubsUser loadClubsUser(RepliesPostId repliesPostId, RepliesWriter repliesWriter);

    ClubsUser loadClubsUser(RepliesId repliesId, RepliesWriter repliesWriter);

    RepliesUserDTO.SimpleInformationDTO loadUserSimpleInformation(Long repliesWriter);

    RetrieveRepliesDTO.RepliesDetailList loadRepliesDetailList(RepliesPostId repliesPostId);
}
