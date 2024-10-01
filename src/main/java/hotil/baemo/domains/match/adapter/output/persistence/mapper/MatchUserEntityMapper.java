package hotil.baemo.domains.match.adapter.output.persistence.mapper;

import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchUserEntity;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchUser;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.MatchUserId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchUserEntityMapper {


    public static List<MatchUserEntity> toEntities(Match match, Long matchId) {
        List<MatchUser> matchUserList = match.getMatchUsers().getUsers();
        return matchUserList.stream()
            .map(user -> toEntity(match, matchId, user))
            .collect(Collectors.toList());
    }

    public static MatchUsers toMatchUsers(List<MatchUserEntity> entityList) {
        return MatchUsers.of(entityList.stream()
            .map(MatchUserEntityMapper::toMatchUser)
            .collect(Collectors.toList()));
    }
    private static MatchUserEntity toEntity(Match match, Long matchId, MatchUser matchUser) {
        return MatchUserEntity.builder()
//            .id(matchUser.getId() != null ? matchUser.getId().id() : null)
            .exerciseId(match.getExerciseId().id())
            .matchId(matchId)
            .userId(matchUser.getUserId().id())
            .team(matchUser.getTeam())
            .matchStatus(match.getMatchStatus())
            .build();
    }

    private static MatchUser toMatchUser(MatchUserEntity entity) {
        return MatchUser.builder()
            .id(new MatchUserId(entity.getId()))
            .userId(new UserId(entity.getUserId()))
            .team(entity.getTeam())
            .build();
    }

}