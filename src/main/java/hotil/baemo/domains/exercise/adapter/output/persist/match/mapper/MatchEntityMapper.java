package hotil.baemo.domains.exercise.adapter.output.persist.match.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchUserEntity;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUser;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchEntityMapper {
    public static MatchEntity toEntity(Match match) {
        return MatchEntity.builder()
            .id(match.getMatchId() != null ? match.getMatchId().id() : null)
            .exerciseId(match.getExerciseId().id())
            .courtNumber(match.getCourtNumber() != null ? match.getCourtNumber().number() : null)
            .matchOrder(match.getOrder().order())
            .matchStatus(match.getMatchStatus())
            .definedTeam(match.isTeamDefined())
            .userLog(match.getMatchUsers().getUsers().stream()
                .map(m -> m.getUserId().id())
                .toList())
            .teamLog(match.getMatchUsers().getUsers().stream()
                .map(MatchUser::getTeam)
                .toList())
            .build();
    }

    public static Match toMatch(MatchEntity entity, List<MatchUserEntity> matchUserEntityList) {
        return Match.builder()
            .matchId(new MatchId(entity.getId()))
            .exerciseId(new ExerciseId(entity.getExerciseId()))
            .order(new Order(entity.getMatchOrder()))
            .courtNumber(entity.getCourtNumber() != null ? new CourtNumber(entity.getCourtNumber()) : null)
            .matchStatus(entity.getMatchStatus())
            .matchUsers(MatchUserEntityMapper.toMatchUsers(matchUserEntityList))
            .build();
    }

    public static Match toMatch(MatchEntity entity) {
        return Match.builder()
            .matchId(new MatchId(entity.getId()))
            .exerciseId(new ExerciseId(entity.getExerciseId()))
            .order(new Order(entity.getMatchOrder()))
            .courtNumber(entity.getCourtNumber() != null ? new CourtNumber(entity.getCourtNumber()) : null)
            .matchStatus(entity.getMatchStatus())
            .build();
    }


    public static List<Match> toMatches(List<MatchEntity> matchList, List<MatchUserEntity> userEntityList) {
        return matchList.stream()
            .map(m -> {
                List<MatchUserEntity> matchingUserEntities = userEntityList.stream()
                    .filter(u -> u.getMatchId().equals(m.getId()))
                    .collect(Collectors.toList());
                return toMatch(m, matchingUserEntities);
            })
            .collect(Collectors.toList());
    }
}
