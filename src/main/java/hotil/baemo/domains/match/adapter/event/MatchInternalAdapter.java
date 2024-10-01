package hotil.baemo.domains.match.adapter.event;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchEntity;
import hotil.baemo.domains.match.adapter.output.persistence.repository.CommandMatchJpaRepository;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchInternalAdapter {

    private final CommandMatchJpaRepository matchRepository;

    public void updateMatchCompletion(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND)
        );
        matchEntity.completeMatch();
    }

    public void updateMatchScoring(Long matchId, String status) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND)
        );
        matchEntity.updateMatchStatus(MatchStatus.valueOf(status));
    }
}
