package hotil.baemo.domains.exercise.adapter.output.persist.user;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchUserRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.repository.ExerciseUserRepository;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandExerciseUserPersistAdapter implements CommandExerciseUserOutPort {

    private final ExerciseUserRepository exerciseUserRepository;
    private final MatchUserRepository matchUserRepository;

    @Override
    public void saveExerciseUser(ExerciseId exerciseId, ExerciseUser user) {
        exerciseUserRepository.save(ExerciseUserEntityMapper.toEntity(exerciseId, user));
    }

    @Override
    public void updateExerciseUser(Match match) {
        List<Long> userIds = match.getMatchUsers().getUserIds().stream()
            .map(UserId::id).toList();
        Long exerciseId = match.getExerciseId().id();
        List<MatchUserEntity> matchUsers = matchUserRepository.findAllByUserIdInAndExerciseId(userIds, exerciseId);
        Map<Long, MatchStatus> matchStatusMap = extractMatchStatus(matchUsers);
        List<ExerciseUserEntity> exerciseUserEntities = exerciseUserRepository.findByExerciseIdAndUserIdIn(exerciseId, userIds);
        exerciseUserEntities.forEach(u -> {
            u.setMatchStatus(matchStatusMap.getOrDefault(u.getUserId(), MatchStatus.NO_MATCH));
        });
    }

    @Override
    public void deleteUser(ExerciseUser user) {
        exerciseUserRepository.delete(ExerciseUserEntityMapper.toEntity(user));
    }


    @Override
    public void deleteAllUsers(List<ExerciseUser> exerciseUsers) {
        exerciseUserRepository.deleteAll(
            exerciseUsers.stream()
                .map(ExerciseUserEntityMapper::toEntity)
                .collect(Collectors.toList())
        );
    }

    private static Map<Long, MatchStatus> extractMatchStatus(List<MatchUserEntity> matchUsers) {
        return matchUsers.stream()
            .collect(Collectors.groupingBy(
                MatchUserEntity::getUserId, Collectors.collectingAndThen(
                    Collectors.minBy(Comparator.comparing(user -> user.getMatchStatus().getPriority())),
                    optional -> {
                        MatchStatus status = optional.map(MatchUserEntity::getMatchStatus)
                            .orElse(MatchStatus.NO_MATCH);

                        return (status == MatchStatus.COMPLETE || status == MatchStatus.HISTORY)
                            ? MatchStatus.NO_MATCH
                            : status;
                    })
            ));
    }
}
