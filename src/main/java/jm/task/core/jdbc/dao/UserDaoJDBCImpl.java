package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection conn = Util.getConnection();

        try {
            conn.createStatement().execute("""
                                    CREATE TABLE IF NOT EXISTS users
                                    (
                                        id INT8 PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(128),
                                        last_name VARCHAR(128),
                                        age TINYINT
                                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Connection conn = Util.getConnection();
        try {
            conn.createStatement().execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection conn = Util.getConnection();
        try {
            PreparedStatement prepareStatement = conn.prepareStatement("INSERT INTO users (name, last_name, age) values (?, ?, ?)");
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Connection conn = Util.getConnection();
        try {
            conn.createStatement().execute("DELETE FROM users WHERE id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Connection conn = Util.getConnection();
        List<User> users = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection conn = Util.getConnection();
        try {
            conn.createStatement().execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
