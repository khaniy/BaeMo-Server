package hotil.baemo.domains.score.adapter.input.rest.mapper;

import hotil.baemo.domains.score.adapter.input.rest.dto.ScoreRequestDTO;
import hotil.baemo.domains.score.domain.aggregate.MatchUser;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;

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
            .matchId(matchId)
            .userId(new UserId(dto.userId()))
            .team(dto.team())
            .build();
    }
}
