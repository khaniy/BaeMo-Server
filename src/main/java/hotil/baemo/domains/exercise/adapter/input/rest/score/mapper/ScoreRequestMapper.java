package hotil.baemo.domains.exercise.adapter.input.rest.score.mapper;

import hotil.baemo.domains.exercise.adapter.input.rest.score.dto.ScoreRequestDTO;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUser;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreRequestMapper {
    public static List<MatchUser> toMatchUsers(MatchId matchId, ScoreRequestDTO.UpdateMatchUser dto) {
        return dto.matchUsers().stream()
            .map(u -> toMatchUser(matchId, u))
            .collect(Collectors.toList());
    }

    public static MatchUser toMatchUser(MatchId matchId, ScoreRequestDTO.MatchUser dto) {
        return MatchUser.builder()
            .userId(new UserId(dto.userId()))
            .team(dto.team())
            .build();
    }
}
