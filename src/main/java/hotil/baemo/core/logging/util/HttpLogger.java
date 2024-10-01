package hotil.baemo.core.logging.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpLogger {

    private static final List<MediaType> READABLE_TYPES = Arrays.asList(
        MediaType.valueOf("text/*"),
        MediaType.APPLICATION_FORM_URLENCODED,
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
        MediaType.valueOf("application/*+json"),
        MediaType.valueOf("application/*+xml"),
        MediaType.MULTIPART_FORM_DATA
    );

    private String httpMethod;
    private String requestUri;

    private String requestBody;
    private String requestParam;

    private HttpStatus httpStatus;
    private String responseBody;


    public static HttpLogger of(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException {
        return HttpLogger.builder()
            .httpMethod(request.getMethod())
            .requestUri(request.getRequestURI())
            .requestBody(getBody(request))
            .requestParam(getParams(request))
            .httpStatus(HttpStatus.valueOf(response.getStatus()))
            .responseBody(getContent(response))
            .build();
    }

    public void log(Double elapsedTime, Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("[REQUEST] :: ").append(httpMethod).append(" <").append(requestUri).append("> ").append(httpStatus.toString()).append(" (").append(elapsedTime).append(" sec)").append(System.lineSeparator());
        sb.append(">> REQUEST_CONTENTS  : ").append("params: ").append(requestParam).append(" bodies: ").append(requestBody).append(System.lineSeparator());
        sb.append(">> RESPONSE_CONTENTS : ").append(responseBody);
        if (e != null) {
            StackTraceElement[] traces = e.getStackTrace();
            sb.append(System.lineSeparator())
                .append(">> EXCEPTION : ").append(e.getMessage()).append(System.lineSeparator());
            for (int i = 0; i < Math.min(20, traces.length); i++) {
                sb.append("    ").append(traces[i].toString()).append(System.lineSeparator());
            }
            log.error(sb.toString());
        } else {
            log.info(sb.toString());
        }
    }


    private static String getBody(ContentCachingRequestWrapper request) throws IOException {
        MediaType mediaType = request.getContentType() != null ? MediaType.valueOf(request.getContentType()) : MediaType.APPLICATION_JSON;
        if (mediaType == MediaType.MULTIPART_FORM_DATA) {
            return "multipart/form-data";
        }
        return new String(request.getContentAsByteArray(), StandardCharsets.UTF_8).replace(System.lineSeparator(), "");
    }

    private static String getParams(ContentCachingRequestWrapper request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        parameterMap.forEach((key, values) -> {
            sb.append(key).append(": ").append(Arrays.toString(values)).append(", ");
        });
        sb.append("}");
        return sb.toString();
    }

    private static String getContent(HttpServletResponse response) {
        ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper) response;

        byte[] data = wrappedResponse.getContentAsByteArray();
        return new String(data);
    }


}
