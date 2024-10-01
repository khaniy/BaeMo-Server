package hotil.baemo.domains.chat.application.usecase.command.exercise;

import java.util.List;

import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface DeleteExerciseChatUseCase {
	void cancelledExierciseMember(UserId userId, ExerciseId exerciseId);
	void deleteExerciseChat(ExerciseId exerciseId);
	void completeExerciseChat(List<Long> exerciseIds);
}
