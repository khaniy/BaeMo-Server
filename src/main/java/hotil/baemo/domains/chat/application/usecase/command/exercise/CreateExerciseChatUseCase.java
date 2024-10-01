package hotil.baemo.domains.chat.application.usecase.command.exercise;

import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CreateExerciseChatUseCase {
	void createExerciseChatRoom(UserId userId, ExerciseId exerciseId);
}
