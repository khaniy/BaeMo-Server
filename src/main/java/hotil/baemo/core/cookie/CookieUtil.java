package hotil.baemo.core.cookie;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {
    private static final String PATH = "/";
    private static final String ENC = "UTF-8";
    private static final int MAX_AGE = 60 * 60 * 24 * 100;
    private static final int MIN_AGE = 0;

    public static String findCookieByKey(final HttpServletRequest request, final String key) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(key)) {
                    return URLDecoder.decode(cookie.getValue(), ENC);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void addCookieKeyAndValue(final HttpServletResponse response, final String key, final String value) {
        try {
            final Cookie cookie = new Cookie(key, URLEncoder.encode(value, ENC));

            cookie.setPath(PATH);
            cookie.setMaxAge(MAX_AGE);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(ResponseCode.COOKIE_ERROR);
        }
    }

    public static void removeCookieByKey(final HttpServletResponse response, final String key) {
        try {
            final Cookie cookie = new Cookie(key, null);

            cookie.setPath(PATH);
            cookie.setMaxAge(MIN_AGE);

            response.addCookie(cookie);
        } catch (Exception e) {
            throw new CustomException(ResponseCode.COOKIE_ERROR);
        }
    }
}