package hotil.baemo.domains.clubs.application.post.ports.input.query;

import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.clubs.application.post.ports.output.command.CommandClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.ports.output.query.QueryClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.ports.output.query.ValidateClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.query.RetrieveClubsPostUseCase;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class RetrieveClubsPostInputPort implements RetrieveClubsPostUseCase {
    private static final String POST_VIEW_COUNT = "POST_VIEW_COUNT";
    private final QueryClubsPostOutputPort queryClubsPostOutputPort;
    private final ValidateClubsPostOutputPort validateClubsPostOutputPort;
    private final CommandClubsPostOutputPort commandClubsPostOutputPort;
    private final BaemoRedis baemoRedis;

    @Override
    public RetrieveClubsPostDTO.PreviewDTO retrievePreview(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable) {
        return queryClubsPostOutputPort.loadPreview(clubsUserId, clubsId, pageable);
    }

    @Override
    public RetrieveClubsPostDTO.TypePreviewDTO retrievePreview(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable) {
        return queryClubsPostOutputPort.loadPreview(clubsUserId, clubsId, type, pageable);
    }

    @Override
    public DetailsClubsPostDTO.Details retrievePost(ClubsPostId clubsPostId, ClubsId clubsId, ClubsUserId clubsUserId) {
        validateClubsPostOutputPort.validDetailView(clubsUserId, clubsPostId);

        final var viewCountKey = getViewCountKey(clubsPostId, clubsUserId);
        if (baemoRedis.notExists(viewCountKey)) {
            baemoRedis.set(viewCountKey, POST_VIEW_COUNT, Duration.ofHours(3L));
            commandClubsPostOutputPort.incrementViewCount(clubsPostId);
        }

        return queryClubsPostOutputPort.loadDetailView(clubsUserId, clubsPostId);
    }

    private String getViewCountKey(ClubsPostId clubsPostId, ClubsUserId clubsUserId) {
        return POST_VIEW_COUNT + ":POST:" + clubsPostId.id() + ":USER:" + clubsUserId.id();
    }
}