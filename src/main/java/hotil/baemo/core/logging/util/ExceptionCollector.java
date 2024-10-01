package hotil.baemo.core.logging.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExceptionCollector {

    private static final String EXCEPTION_ATTRIBUTE = "exceptionCollector";

    public void add(HttpServletRequest request, Exception e) {
        request.setAttribute(EXCEPTION_ATTRIBUTE, e);
    }

    public Exception get(HttpServletRequest request) {
        if (isEmpty(request))
            return null;
        return (Exception) request.getAttribute(EXCEPTION_ATTRIBUTE);
    }

    private Boolean isEmpty(HttpServletRequest request) {
        return request.getAttribute(EXCEPTION_ATTRIBUTE) == null;
    }
}