package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static final String PASSWORD = "pass";
    private static final String USER = "root";
    private static final String URL_DB = "jdbc:mysql://localhost/kaoridb?";

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            System.err.println("ReflectiveOperationException: " + ex.getMessage());
        }

        try {
            connection = DriverManager.getConnection(URL_DB, USER, PASSWORD);
            return connection;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            throw new RuntimeException(ex);
        }
    }
}
