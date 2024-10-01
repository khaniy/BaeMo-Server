package hotil.baemo.domains.clubs.adapter.post.output.persistence.dsl;

import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryClubsPostDslRepository {
    List<RetrieveClubsPostDTO.PreviewNoticeDTO> loadPreviewNoticeDTOList(ClubsId clubsId);

    List<RetrieveClubsPostDTO.PreviewClubsPostDTO> loadPreviewClubsPostDTOList(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable);

    List<RetrieveClubsPostDTO.PreviewClubsPostDTO> loadFilteredPreviewClubsPostDTOList(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable);

    DetailsClubsPostDTO.WriterDTO loadWriter(ClubsPostId clubsPostId);

    DetailsClubsPostDTO.PostDTO loadPost(ClubsPostId clubsPostId);

    DetailsClubsPostDTO.RepliesList loadRepliesList(ClubsPostId clubsPostId);
}
