package hotil.baemo.domains.match.adapter.event.mapper;

import hotil.baemo.domains.match.adapter.event.dto.MatchEventDTO;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.user.UserId;

public class MatchEventMapper {

    public static MatchEventDTO.StatusUpdated toStatusUpdated(Match match) {
        return MatchEventDTO.StatusUpdated.builder()
            .matchId(match.getMatchId().id())
            .courtNumber(match.getCourtNumber().number())
            .order(match.getOrder().order())
            .matchUserIds(match.getUserIds().stream().map(UserId::id).toList())
            .matchStatus(match.getMatchStatus().toString())
            .build();
    }
}