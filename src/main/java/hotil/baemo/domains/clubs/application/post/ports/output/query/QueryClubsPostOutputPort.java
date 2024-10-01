package hotil.baemo.domains.clubs.application.post.ports.output.query;

import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import org.springframework.data.domain.Pageable;

public interface QueryClubsPostOutputPort {
    ClubsPost load(ClubsPostId clubsPostId);

    RetrieveClubsPostDTO.PreviewDTO loadPreview(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable);

    RetrieveClubsPostDTO.TypePreviewDTO loadPreview(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable);

    DetailsClubsPostDTO.Details loadDetailView(ClubsUserId clubsUserId, ClubsPostId clubsPostId);
}
