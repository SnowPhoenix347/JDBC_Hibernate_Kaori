package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn;

    public UserDaoJDBCImpl() {
        conn = Util.getConnection();
    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.execute("""
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
        try (Statement statement = conn.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) values (?, ?, ?)";
        try (PreparedStatement prepareStatement = conn.prepareStatement(sql)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                users.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (var a = conn.createStatement()) {
            a.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
