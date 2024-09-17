package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Util {
    private static Connection connection;

    public static Optional<Connection> getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            System.err.println("ReflectiveOperationException: " + ex.getMessage());
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kaoridb?" +
                    "user=root&password=pass");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return Optional.ofNullable(connection);
    }
}
