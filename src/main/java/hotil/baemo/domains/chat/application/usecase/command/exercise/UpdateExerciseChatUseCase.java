package hotil.baemo.domains.chat.application.usecase.command.exercise;

import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface UpdateExerciseChatUseCase {
	void updateExerciseChatMember(UserId userId, ExerciseId exerciseId, ChatRole chatRole);
	void updateExerciseChatMemberRole(UserId userId, ExerciseId exerciseId, ChatRole chatRole);
}
