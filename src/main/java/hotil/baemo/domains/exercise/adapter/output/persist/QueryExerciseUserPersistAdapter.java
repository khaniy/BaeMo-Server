package hotil.baemo.domains.exercise.adapter.output.persist;

import hotil.baemo.domains.exercise.adapter.output.persist.repository.QueryExerciseUserQRepository;
import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QueryExerciseUserPersistAdapter implements RetrieveExerciseUserOutputPort {

    private final QueryExerciseUserQRepository queryExerciseUserRepository;

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getWaitingMembers(ExerciseId exerciseId) {
        return queryExerciseUserRepository.findWaitingMembers(exerciseId.id());
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getPendingMembers(ExerciseId exerciseId) {
        return queryExerciseUserRepository.findPendingMembers(exerciseId.id());
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getPendingGuests(ExerciseId exerciseId) {
        return queryExerciseUserRepository.findPendingGuests(exerciseId.id());
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getParticipatedMembers(ExerciseId exerciseId) {
        return queryExerciseUserRepository.findParticipatedUsers(exerciseId.id());
    }

    @Override
    public List<QExerciseUserDTO.ExerciseMatchUserListView> getMatchUsers(ExerciseId exerciseId) {
        return queryExerciseUserRepository.findMatchUsers(exerciseId.id());
    }
}
