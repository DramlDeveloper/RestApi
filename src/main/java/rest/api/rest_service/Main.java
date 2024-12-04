package rest.api.rest_service;

import rest.api.rest_service.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void checkMetaData() {
        try (Connection connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs().getMetaData();
            var value = metaData.getClientInfoProperties();

            while (value.next()) {
                System.out.println(value.getString(4));
            }

                System.out.println(catalogs.getColumnClassName(1));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        checkMetaData();
    }
}
