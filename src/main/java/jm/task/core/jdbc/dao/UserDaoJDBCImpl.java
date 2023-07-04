package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnectionDB;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement;
        try (Connection connection = getConnectionDB()) {
            statement = connection.createStatement();
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                    "name VARCHAR(30) NOT NULL, " +
                    "lastName VARCHAR(30) NOT NULL, " +
                    "age INT(3) NOT NULL)";
            statement.executeUpdate(createUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Statement statement;
        try (Connection connection = getConnectionDB()) {
            statement = connection.createStatement();
            String dropUsersTable = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(dropUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String str = "insert into users(name, lastName, age) values(?,?,?)";

        try (Connection connect = getConnectionDB();
             PreparedStatement st = connect.prepareStatement(str)) {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Statement statement;
        try (Connection connection = getConnectionDB()) {
            statement = connection.createStatement();
            String removeUserById = "DELETE FROM users WHERE id = " + id;
            statement.executeUpdate(removeUserById);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Statement statement = getConnectionDB().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while ((resultSet.next())) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        Statement statement;
        try (Connection connection = getConnectionDB()) {
            statement = connection.createStatement();
            String cleanUsersTable = "DELETE FROM users";
            statement.executeUpdate(cleanUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}