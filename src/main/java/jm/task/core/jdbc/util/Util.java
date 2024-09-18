package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static final String PASSWORD = "pass";
    private static final String USER = "root";
    private static final String URL_DB = "jdbc:mysql://localhost/kaoridb?";
    private static final String JDBC_URL = ("jdbc:mysql://localhost/kaoridb?" +
                                            "user=" + USER + "&password=" + PASSWORD);

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

    private static SessionFactory sessionFactory;

    public static void configureHibernate() {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", JDBC_URL);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "none");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.current_session_context_class", "thread");

            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}