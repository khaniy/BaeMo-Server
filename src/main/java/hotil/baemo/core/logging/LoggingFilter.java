package hotil.baemo.core.logging;

import hotil.baemo.core.logging.util.ExceptionCollector;
import hotil.baemo.core.logging.util.HttpLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    private static final List<String> EXCEPT_URLS = List.of("/system", "/doc", "/swagger-ui", "/v3", "/cron");
    private static final List<String> SSE_URLS = List.of("/api/match/connect");
    private final ExceptionCollector exceptionCollector;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isSseRequest(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(
                filterChain,
                new ContentCachingRequestWrapper(request),
                new ContentCachingResponseWrapper(response));
        }
    }

    private void doFilterWrapped(FilterChain filterChain, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws ServletException, IOException {
        Timer timer = Timer.start();
        try {
            filterChain.doFilter(request, response);
            if (isUrlLoggable(request)) {
                var elapsedTime = timer.end();
                Exception exception = exceptionCollector.get(request);
                HttpLogger.of(request, response).log(elapsedTime, exception);
            }
        } catch (Exception e) {
            var elapsedTime = timer.end();
            HttpLogger.of(request, response).log(elapsedTime, e);
            throw e;
        }
        response.copyBodyToResponse();
    }

    private boolean isUrlLoggable(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return EXCEPT_URLS.stream().noneMatch(requestURI::contains);
    }

    private boolean isSseRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return SSE_URLS.stream().anyMatch(requestURI::contains);
    }

    @AllArgsConstructor
    private static class Timer {
        private long startTime;

        private static Timer start() {
            return new Timer(System.currentTimeMillis());
        }

        private double end() {
            return (System.currentTimeMillis() - startTime) / 1000.0;
        }

    }
}

