package hotil.baemo.domains.exercise.adapter.output.persist.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.mapper.MatchEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchUserRepository;
import hotil.baemo.domains.exercise.application.ports.output.match.LoadMatchOutPort;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrder;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadMatchPersistAdapter implements LoadMatchOutPort {

    private final MatchRepository matchRepository;
    private final MatchUserRepository matchUserRepository;

    @Override
    public Match loadMatch(MatchId matchId) {
        var match = matchRepository.findById(matchId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND));
        var users = matchUserRepository.findAllByMatchId(matchId.id());
        return MatchEntityMapper.toMatch(match, users);
    }

    @Override
    public MatchOrders loadMatchOrdersByExerciseId(ExerciseId exerciseId) {
        List<MatchEntity> orders = matchRepository.findAllByExerciseId(exerciseId.id());

        return MatchOrders.of(orders.stream()
            .map(e -> MatchOrder.of(new MatchId(e.getId()), new Order(e.getMatchOrder())))
            .collect(Collectors.toList()));
    }

    @Override
    public Boolean existProgressMatch(ExerciseCourt exerciseCourt) {
        return matchRepository.existProgressMatch(exerciseCourt.getExerciseId(), exerciseCourt.getNumber());
    }
}
