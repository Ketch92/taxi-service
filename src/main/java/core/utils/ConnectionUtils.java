package core.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        String user = "postgres";
        String password = "Qwest900";
        dbProperties.setProperty("user", user);
        dbProperties.setProperty("password", password);
        String url = "jdbc:postgresql://localhost:5432/taxi_service";
        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Fail establishing connection to database ", e);
        }
    }
}
