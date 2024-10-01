package hotil.baemo.domains.exercise.application.ports.output;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;

import java.util.List;

public interface RetrieveExerciseUserOutputPort {
    List<QExerciseUserDTO.ExerciseUserListView> getWaitingMembers(ExerciseId exerciseId);
    List<QExerciseUserDTO.ExerciseUserListView> getPendingMembers(ExerciseId exerciseId);
    List<QExerciseUserDTO.ExerciseUserListView> getPendingGuests(ExerciseId exerciseId);
    List<QExerciseUserDTO.ExerciseUserListView> getParticipatedMembers(ExerciseId exerciseId);

    List<QExerciseUserDTO.ExerciseMatchUserListView> getMatchUsers(ExerciseId exerciseId);
}
