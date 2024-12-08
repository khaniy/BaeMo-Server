package hotil.baemo.domains.clubs.application.ports.output.comment;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;

public interface CommandClubPostCommentOutPort {
    QClubPostCommentDTO.Create save(ClubPostComment replies);

    void delete(ClubPostComment replies);
}
