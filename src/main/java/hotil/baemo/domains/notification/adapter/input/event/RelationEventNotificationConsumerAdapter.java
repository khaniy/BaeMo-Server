package hotil.baemo.domains.notification.adapter.input.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.core.event.RelationTopic;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.notification.application.usecase.NotifyExerciseUseCase;
import hotil.baemo.domains.notification.application.usecase.NotifyRelationUseCase;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseLocation;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTime;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseType;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelationEventNotificationConsumerAdapter {

	private final NotifyRelationUseCase notifyRelationUseCase;
	@Async
	@EventListener
	public void sendFriendRequest(RelationTopic.sendFriendRequestEvent event) {
		notifyRelationUseCase.notifyFriendRequest(
			new UserId(event.userId()),
			new UserId(event.userId())
		);
	}

	@Async
	@EventListener
	public void friendRequestApproved(RelationTopic.friendRequestApprovedEvent event) {
		notifyRelationUseCase.notifyFriendRequestApproved(
			new UserId(event.userId()),
			new UserId(event.userId())
		);
	}

}
