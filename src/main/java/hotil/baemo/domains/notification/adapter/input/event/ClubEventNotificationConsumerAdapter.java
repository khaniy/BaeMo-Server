package hotil.baemo.domains.notification.adapter.input.event;

import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.domains.notification.application.usecase.NotifyClubUseCase;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubEventNotificationConsumerAdapter {

    private final NotifyClubUseCase notifyClubUseCase;

    @Async
    @EventListener
    public void clubMemberApplied(ClubTopic.JoinRequestedEvent event) {
        notifyClubUseCase.notifyApplyingToClubManagers(
            new ClubId(event.clubsId()),
            new UserId(event.userId())
        );
    }

    @Async
    @EventListener
    public void clubMemberApproved(ClubTopic.JoinedEvent event) {
        notifyClubUseCase.notifyApproveToTargetUser(
            new ClubId(event.clubsId()),
            new UserId(event.userId())
        );
        notifyClubUseCase.notifyJoinToClubManagers(
            new ClubId(event.clubsId()),
            new UserId(event.userId())
        );
    }

    @Async
    @EventListener
    public void clubMemberExpelled(ClubTopic.UserExpelledEvent event) {
        notifyClubUseCase.notifyExpelledToTargetUser(
            new ClubId(event.clubsId()),
            new UserId(event.userId())
        );
    }

    @Async
    @EventListener
    public void clubMemberLeft(ClubTopic.UserLeftEvent event) {
        notifyClubUseCase.notifyLeftToClubManagers(
            new ClubId(event.clubsId()),
            new UserId(event.userId())
        );
    }


    @Async
    @EventListener
    public void clubPostCreated(ClubTopic.PostCreated event) {
        notifyClubUseCase.notifyPostCreationToClubMembers(
            new ClubId(event.clubsId()),
            new ClubPostId(event.clubPostId()),
            new UserId(event.userId()),
            new PostTitle(event.title()),
            new ThumbnailText(event.content())
        );
    }

    @Async
    @EventListener
    public void clubPostCommented(ClubTopic.PostCommented event) {
        notifyClubUseCase.notifyPostCommentedToWriter(
            new ClubId(event.clubsId()),
            new ClubPostId(event.clubPostId()),
            new UserId(event.userId()),
            new PostTitle(event.title()),
            new ThumbnailText(event.content())
        );
    }

}
