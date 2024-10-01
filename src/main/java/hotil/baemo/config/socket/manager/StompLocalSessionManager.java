package hotil.baemo.config.socket.manager;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class StompLocalSessionManager {

    private final ConcurrentMap<String, WebSocketSession> localSessionStorage = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        localSessionStorage.put(session.getId(), session);
        log.info("Connected Session Id = {}, Total_Session = {}", session.getId(), getSessions());
    }

    public void disconnectSessions(List<String> sessionIds) {
        sessionIds.forEach(this::disconnectSession);
    }

    public List<String> getSessions() {
        return new ArrayList<>(localSessionStorage.keySet());
    }

    public void popSession(WebSocketSession session) {
        localSessionStorage.remove(session.getId());
        log.info("Disconnected Session Id = {}, Total_Session = {}", session.getId(), getSessions());
    }

    @SneakyThrows
    public void disconnectSession(String sessionId) {
        WebSocketSession session = localSessionStorage.get(sessionId);
        if (session != null && session.isOpen()) {
            session.close(CloseStatus.POLICY_VIOLATION);
            localSessionStorage.remove(sessionId);
            log.info("Disconnected Session Id = {}, Total_Session = {}", sessionId, getSessions());
        }
    }
}