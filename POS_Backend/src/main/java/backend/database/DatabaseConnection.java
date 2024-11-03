package backend.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String url;
    private static String username;
    private static String password;

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/db_config.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

            // Registrar el driver JDBC si es necesario
            String driver = properties.getProperty("db.driver");
            if (driver != null) {
                Class.forName(driver);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
