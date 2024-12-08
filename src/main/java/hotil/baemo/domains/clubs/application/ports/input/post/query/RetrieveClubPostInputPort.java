package hotil.baemo.domains.clubs.application.ports.input.post.query;

import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.QueryClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.post.query.RetrieveClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class RetrieveClubPostInputPort implements RetrieveClubPostUseCase {
    private static final String POST_VIEW_COUNT = "POST_VIEW_COUNT";

    private final QueryClubPostOutputPort queryClubPostOutputPort;
    private final CommandClubPostOutputPort commandClubPostOutputPort;
    private final BaemoRedis baemoRedis;

    @Override
    public QClubPostDTO.ClubPostMain retrievePreview(UserId userId, ClubId clubId, Pageable pageable) {
        return queryClubPostOutputPort.loadPreview(userId, clubId, pageable);
    }

    @Override
    public QClubPostDTO.ClubPostFiltered retrievePreview(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable) {
        return queryClubPostOutputPort.loadPreview(userId, clubId, type, pageable);
    }

    @Override
    public QClubPostDTO.ClubPostDetailView retrievePost(ClubPostId clubPostId, ClubId clubId, UserId userId) {
        final var viewCountKey = getViewCountKey(clubPostId, userId);
        if (baemoRedis.notExists(viewCountKey)) {
            baemoRedis.set(viewCountKey, POST_VIEW_COUNT, Duration.ofHours(3L));
            commandClubPostOutputPort.incrementViewCount(clubPostId);
        }

        return queryClubPostOutputPort.loadDetailView(userId, clubPostId);
    }

    private String getViewCountKey(ClubPostId clubPostId, UserId userId) {
        return POST_VIEW_COUNT + ":POST:" + clubPostId.id() + ":USER:" + userId.id();
    }
}