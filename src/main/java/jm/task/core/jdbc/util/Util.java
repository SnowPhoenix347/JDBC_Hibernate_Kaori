package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;
    private static final String password = "pass";
    private static final String user = "root";

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            System.err.println("ReflectiveOperationException: " + ex.getMessage());
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kaoridb?" +
                    "user=" + user + "&password=" + password);
            return connection;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            throw new RuntimeException(ex);
        }
    }
}
