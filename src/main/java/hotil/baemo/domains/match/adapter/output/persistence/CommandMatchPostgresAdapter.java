package hotil.baemo.domains.match.adapter.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.adapter.output.external.query.MatchExternalQuery;
import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchEntity;
import hotil.baemo.domains.match.adapter.output.persistence.mapper.MatchEntityMapper;
import hotil.baemo.domains.match.adapter.output.persistence.mapper.MatchUserEntityMapper;
import hotil.baemo.domains.match.adapter.output.persistence.repository.CommandMatchJpaRepository;
import hotil.baemo.domains.match.adapter.output.persistence.repository.CommandMatchUserJpaRepository;
import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrder;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.value.club.ClubId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.match.Order;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hotil.baemo.config.cache.CacheProperties.CacheValue.SCOREBOARD_CACHE;

@Service
@RequiredArgsConstructor
public class CommandMatchPostgresAdapter implements CommandMatchOutPort {
    private final CommandMatchJpaRepository matchRepository;
    private final CommandMatchUserJpaRepository matchUserRepository;
    private final MatchExternalQuery matchExternalQuery;

    @Override
    @Caching(evict = {
        @CacheEvict(
            value = SCOREBOARD_CACHE,
            key = "'match_users_'+#match.matchId.id",
            condition = "#match?.matchId != null"
        ),
        @CacheEvict(
            value = SCOREBOARD_CACHE,
            key = "'match_detail_'+#match.matchId.id",
            condition = "#match?.matchId != null"
        )
    })
    public MatchId saveMatch(Match match) {
        var entity = matchRepository.save(MatchEntityMapper.toEntity(match));
        matchUserRepository.deleteByMatchId(entity.getId());
        matchUserRepository.saveAll(MatchUserEntityMapper.toEntities(match, entity.getId()));
        return new MatchId(entity.getId());
    }

    @Override
    public void saveMatchOrder(MatchOrders matchOrders) {
        var matches = matchRepository.findAllById(matchOrders.getMatchOrders().stream()
            .map(m -> m.getMatchId().id())
            .toList()
        );
        matches.stream()
            .filter(m -> matchOrders.contains(new MatchId(m.getId())))
            .forEach(m -> m.updateMatchOrder(matchOrders.getOrder(new MatchId(m.getId()))));
        matchRepository.saveAll(matches);
    }

    @Override
    public void saveCompletedExerciseMatches(List<Long> exerciseIds) {
        matchRepository.updateCompletedMatchToHistoryMatchByExerciseIds(exerciseIds);
        var matches = matchRepository.findNotCompletedMatchByExerciseIds(exerciseIds);
        matchRepository.deleteAll(matches);
        matchUserRepository.deleteAllByMatches(matches.stream().map(MatchEntity::getId).collect(Collectors.toList()));
    }

    @Override
    public Match getMatch(MatchId matchId) {
        var match = matchRepository.findById(matchId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND));
        var users = matchUserRepository.findAllByMatchId(matchId.id());
        return MatchEntityMapper.toMatch(match, users);
    }

    @Override
    public List<Match> getMatches(List<MatchId> matchIds) {
        var matches = matchRepository.findAllByIds(matchIds.stream().map(MatchId::id).toList());
        return matches.stream()
            .map(MatchEntityMapper::toMatch)
            .collect(Collectors.toList());
    }

    @Override
    public MatchOrders getMatchOrdersByExerciseId(ExerciseId exerciseId) {
        List<Object[]> orders = matchRepository.findMatchOrderByExerciseId(exerciseId.id());

        return MatchOrders.of(orders.stream()
            .map(e -> MatchOrder.of(new MatchId((Long) e[0]), new Order((Integer) e[1])))
            .collect(Collectors.toList()));
    }

    @Override
    public void deleteMatch(Match match) {
        matchRepository.deleteById(match.getMatchId().id());
        matchUserRepository.deleteByMatchId(match.getMatchId().id());
    }

    @Override
    public List<MatchId> deleteMatchesByExerciseId(ExerciseId exerciseId) {
        var matches = matchRepository.findAllByExerciseId(exerciseId.id());
        matchUserRepository.deleteAllByExerciseId(exerciseId.id());
        matchRepository.deleteAll(matches);
        return matches.stream()
            .map(e -> new MatchId(e.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteMatchUserByExerciseId(ExerciseId exerciseId, UserId userId) {
        var matches = matchRepository.findAllByExerciseId(exerciseId.id());
        List<Long> matchIds = matches.stream().map(MatchEntity::getId).toList();
        matchUserRepository.deleteAllByMatchesAndExercise(matchIds, userId.id());
        matches.stream().forEach(MatchEntity::undefinedTeam);
        matchRepository.saveAll(matches);
    }

    @Override
    public void deleteMatchUserByClubId(ClubId clubId, UserId userId) {
        var exerciseIds = matchExternalQuery.findNotCompletedExercisesByClubId(clubId.id());
        var matches = matchRepository.findAllByExerciseIds(exerciseIds);
        List<Long> matchIds = matches.stream().map(MatchEntity::getId).toList();
        matchUserRepository.deleteAllByMatchesAndExercise(matchIds, userId.id());
        matches.stream().forEach(MatchEntity::undefinedTeam);
        matchRepository.saveAll(matches);
    }
}
