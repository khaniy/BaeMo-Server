package hotil.baemo.domains.score.adapter.event.sse;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.score.adapter.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.score.adapter.event.sse.repository.ScoreBoardSseLocalRepository;
import hotil.baemo.domains.score.adapter.output.external.query.ScoreExternalQuery;
import hotil.baemo.domains.score.application.port.output.ScoreBoardSSEOutPort;
import hotil.baemo.domains.score.application.usecase.EndScoreBoardUseCase;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreBoardSSEAdapter implements ScoreBoardSSEOutPort {

    private static final Long TIMEOUT = 1200000L; //20분
    private final ScoreBoardSseLocalRepository scoreBoardSseLocalRepository;
    private final ScoreExternalQuery scoreExternalQuery;
    private final EndScoreBoardUseCase endScoreBoardUseCase;


    @Override
    public SseEmitter connect(UserId userId, ScoreBoard scoreBoard) {
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        sseEmitter.onCompletion(() -> delete(userId, scoreBoard));
        sseEmitter.onTimeout(() -> delete(userId, scoreBoard));
        sseEmitter.onError((ex) -> delete(userId, scoreBoard));
        scoreBoardSseLocalRepository.save(scoreBoard.getMatchId().id(), userId.id(), sseEmitter);
        final var init = scoreExternalQuery.findScoreInit(scoreBoard.getMatchId().id(), scoreBoard);
        sendMessage(sseEmitter, BaeMoObjectUtil.writeValueAsString(init));
        return sseEmitter;
    }

    @Override
    public void disconnectAllByMatch(MatchId matchId) {
        List<SseEmitter> emitters = scoreBoardSseLocalRepository.findEmittersByMatchId(matchId.id());
        emitters.forEach(SseEmitter::complete);
    }

    @Override
    public void disconnectAllByExerciseId(List<ExerciseId> exerciseIds) {
        List<Long> matchIds = scoreExternalQuery.getMatchIds(exerciseIds.stream().map(ExerciseId::id).toList());
        List<SseEmitter> emitters = scoreBoardSseLocalRepository.findEmittersByMatchIds(matchIds);
        emitters.forEach(SseEmitter::complete);

    }

    public void sendMessage(EventScoreBoardDTO.Updated dto) {
        Long matchId = dto.matchId();
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        List<SseEmitter> emitters = scoreBoardSseLocalRepository.findEmittersByMatchId(matchId);
        emitters.forEach(emitter -> {
            sendMessage(emitter, message);
        });
    }

    private void delete(UserId userId, ScoreBoard scoreBoard) {
        scoreBoardSseLocalRepository.delete(scoreBoard.getMatchId().id(), userId.id());
        if (scoreBoard.getRefereeId().equals(userId)) {
            endScoreBoardUseCase.stopScoreBoard(scoreBoard.getMatchId(), userId);
        }
    }

    private void sendMessage(SseEmitter sseEmitter, String message) {
        try {
            sseEmitter.send(SseEmitter.event()
                .name("score-connect")
                .data(message)
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            sseEmitter.completeWithError(new CustomException(ResponseCode.ETC_ERROR));
        }
    }
}
