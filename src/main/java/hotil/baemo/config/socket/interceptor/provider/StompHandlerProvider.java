package hotil.baemo.config.socket.interceptor.provider;

import hotil.baemo.config.socket.error.HandleCustomException;
import hotil.baemo.config.socket.interceptor.handler.StompHandler;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandlerProvider {
    private final List<StompHandler> handlers;
    //TODO 추후 삭제 - hadler 정보 보기 위함 용도
    @PostConstruct
    public void logSupportedHandlers() {
        log.info("StompHandlers:");
        handlers.forEach(handler -> log.info("- {}", handler.getClass().getSimpleName()));
    }


    @HandleCustomException
    public StompHandler getHandler(StompHeaderAccessor accessor) {
        System.out.println("handlers = " + handlers);
        return handlers.stream()
                .filter(handler -> handler.supports(accessor))
                .findFirst()
                .orElseThrow(() -> new CustomException(ResponseCode.ETC_ERROR));
    }
}
