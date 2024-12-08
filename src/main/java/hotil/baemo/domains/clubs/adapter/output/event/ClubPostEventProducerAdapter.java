package hotil.baemo.domains.clubs.adapter.output.event;

import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostEventOutputPort;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubPostEventProducerAdapter implements CommandClubPostEventOutputPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendCreatedEvent(ClubPost clubPost) {
        eventPublisher.publishEvent(ClubTopic.PostCreated.builder()
            .clubsId(clubPost.getClubId().clubsId())
            .clubPostId(clubPost.getClubPostId().id())
            .userId(clubPost.getWriterId().id())
            .title(clubPost.getClubPostTitle().title())
            .content(clubPost.getClubPostContent().content())
            .build());
    }

    @Override
    public void sendCommentedEvent(ClubPost clubPost, ClubPostComment clubPostComment) {
        eventPublisher.publishEvent(ClubTopic.PostCommented.builder()
            .clubsId(clubPost.getClubId().clubsId())
            .clubPostId(clubPost.getClubPostId().id())
            .userId(clubPostComment.getWriterId().id())
            .title(clubPost.getClubPostTitle().title())
            .content(clubPostComment.getCommentContent().content())
            .build());
    }
}