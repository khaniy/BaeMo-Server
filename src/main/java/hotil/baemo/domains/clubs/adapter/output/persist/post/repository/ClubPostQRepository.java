package hotil.baemo.domains.clubs.adapter.output.persist.post.repository;

import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubPostQRepository {
    List<QClubPostDTO.ClubNoticeListView> loadPreviewNoticeDTOList(ClubId clubId, UserId userId);

    List<QClubPostDTO.ClubPostListView> loadPreviewClubsPostDTOList(UserId userId, ClubId clubId, Pageable pageable);

    List<QClubPostDTO.ClubPostListView> loadFilteredPreviewClubsPostDTOList(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable);

    QClubPostDTO.ClubPostDetailView loadPost(ClubPostId clubPostId, UserId userId);
}
