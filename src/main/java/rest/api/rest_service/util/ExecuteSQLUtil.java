package rest.api.rest_service.util;


import rest.api.rest_service.db.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public final class ExecuteSQLUtil {

    private ExecuteSQLUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void executeScriptSQL() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();
             InputStream fileSql = ConnectionManager.class.getClassLoader().getResourceAsStream("script.sql")) {
            var script = new String(Objects.requireNonNull(fileSql).readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(script);
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error while executing script", e);
        }
    }
}