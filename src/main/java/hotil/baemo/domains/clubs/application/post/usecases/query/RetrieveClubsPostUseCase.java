package hotil.baemo.domains.clubs.application.post.usecases.query;

import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface RetrieveClubsPostUseCase {
    RetrieveClubsPostDTO.PreviewDTO retrievePreview(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable);

    RetrieveClubsPostDTO.TypePreviewDTO retrievePreview(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable);

    DetailsClubsPostDTO.Details retrievePost(ClubsPostId clubsPostId, ClubsId clubsId, ClubsUserId clubsUserId);
}
