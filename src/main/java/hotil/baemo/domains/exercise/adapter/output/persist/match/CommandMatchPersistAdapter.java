package hotil.baemo.domains.exercise.adapter.output.persist.match;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.mapper.MatchEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.match.mapper.MatchUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchUserRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.score.repository.CommandScoreJpaRepository;
import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandMatchPersistAdapter implements CommandMatchOutPort {
    private final MatchRepository matchRepository;
    private final MatchUserRepository matchUserRepository;
    private final CommandScoreJpaRepository commandScoreJpaRepository;

    @Override
    public MatchId saveMatch(Match match) {
        var entity = matchRepository.save(MatchEntityMapper.toEntity(match));
        matchUserRepository.deleteAllByMatchId(entity.getId());
        matchUserRepository.saveAll(MatchUserEntityMapper.toEntities(match, entity.getId()));
        return new MatchId(entity.getId());
    }

    @Override
    public void saveMatchOrder(MatchOrders matchOrders) {
        var matches = matchRepository.findAllByIdIn(matchOrders.getMatchOrders().stream()
            .map(m -> m.getMatchId().id())
            .toList()
        );
        matches.stream()
            .filter(m -> matchOrders.contains(new MatchId(m.getId())))
            .forEach(m -> m.updateMatchOrder(matchOrders.getOrder(new MatchId(m.getId()))));
        matchRepository.saveAll(matches);
    }

    @Override
    public void deleteMatch(Match match) {
        matchRepository.deleteById(match.getMatchId().id());
        matchUserRepository.deleteAllByMatchId(match.getMatchId().id());
        commandScoreJpaRepository.deleteByMatchId(match.getMatchId().id());
    }

    @Override
    public void deleteMatchesByExerciseId(ExerciseId exerciseId) {
        var matches = matchRepository.findAllByExerciseId(exerciseId.id());
        matchUserRepository.deleteAllByExerciseId(exerciseId.id());
        matchRepository.deleteAll(matches);
        commandScoreJpaRepository.deleteAllByMatchIdIn(matches.stream().map(MatchEntity::getId).toList());
    }

    @Override
    public void deleteMatchUserByExerciseId(ExerciseId exerciseId, UserId userId) {
        var matches = matchRepository.findAllByExerciseId(exerciseId.id());
        matches.stream().forEach(MatchEntity::undefinedTeam);
        matchUserRepository.deleteAllByUserIdAndExerciseId(userId.id(), exerciseId.id());
    }
}
