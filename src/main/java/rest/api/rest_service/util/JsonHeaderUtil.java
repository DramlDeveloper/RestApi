package rest.api.rest_service.util;

import jakarta.servlet.http.HttpServletResponse;

public final class JsonHeaderUtil {

    private JsonHeaderUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void jsonHeader(HttpServletResponse resp) {
        resp.addHeader("Content-Type", "application/json");
        resp.setCharacterEncoding("UTF-8");
    }
}
