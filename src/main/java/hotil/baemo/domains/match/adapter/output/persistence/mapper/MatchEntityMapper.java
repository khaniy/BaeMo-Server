package hotil.baemo.domains.match.adapter.output.persistence.mapper;

import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchUserEntity;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchUser;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.match.Order;
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
            .courtNumber(match.getCourtNumber().number())
            .matchOrder(match.getOrder().order())
            .matchStatus(match.getMatchStatus())
            .definedTeam(match.isTeamDefined())
            .userLog(match.getMatchUsers().getUsers().stream()
                .map(m->m.getUserId().id())
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
            .courtNumber(new CourtNumber(entity.getCourtNumber()))
            .matchStatus(entity.getMatchStatus())
            .matchUsers(MatchUserEntityMapper.toMatchUsers(matchUserEntityList))
            .build();
    }

    public static Match toMatch(MatchEntity entity) {
        return Match.builder()
            .matchId(new MatchId(entity.getId()))
            .exerciseId(new ExerciseId(entity.getExerciseId()))
            .order(new Order(entity.getMatchOrder()))
            .courtNumber(new CourtNumber(entity.getCourtNumber()))
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
