package hotil.baemo.domains.score.adapter.event.sse.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreBoardSseLocalRepository {

    private final Map<Long, List<Session>> storage = new ConcurrentHashMap<>();

    public void save(Long matchId, Long userId, SseEmitter sseEmitter) {
        storage.computeIfAbsent(matchId, k -> Collections.synchronizedList(new ArrayList<>()))
            .add(new Session(userId, sseEmitter));
        log.info("Save scoreboard sse: {}", storage);
    }


    public void delete(Long matchId, Long userId) {
        List<Session> sessions = storage.get(matchId);
        if (sessions != null) {
            sessions.removeIf(session -> session.userId().equals(userId));
            if (sessions.isEmpty()) {
                storage.remove(matchId);
            }
        }
        log.info("Delete scoreboard sse: {}", storage);
    }

    public List<SseEmitter> findEmittersByMatchId(Long matchId) {
        List<Session> sessions = storage.get(matchId);
        if (sessions != null) {
            return sessions.stream()
                .map(Session::sseEmitter)
                .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public List<SseEmitter> findEmittersByMatchIds(List<Long> matchIds) {
        return matchIds.stream()
            .map(storage::get)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .map(Session::sseEmitter)
            .collect(Collectors.toList());
    }

    private record Session(
        Long userId,
        SseEmitter sseEmitter
    ) {
    }

}
