package com.academy.repository;

import com.academy.entity.user.Level;
import com.academy.entity.user.User;
import com.academy.entity.user.UserLevel;
import com.academy.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements RepositoryOperations<User> {

    @Override
    public boolean create(User user) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, " +
                    "password, user_level) VALUES (?,?,?)");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserLevel().toString());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return result;
        }

    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE login=?");
            statement.setString(1, user.getLogin());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean update(User oldUser, User updUser) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET login=?, " +
                    "password=?, user_level=? WHERE login=?");
            statement.setString(1, updUser.getLogin());
            statement.setString(2, updUser.getPassword());
            statement.setString(3, updUser.getUserLevel().toString());
            statement.setString(4, oldUser.getLogin());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User read(User user) {
        User userResult = new User();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id_users, login, password, user_level " +
                    "FROM users WHERE login=?");
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_users");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String userLevel = resultSet.getString("user_level");
                userResult.setId(id);
                userResult.setLogin(login);
                userResult.setPassword(password);
                userResult.setUserLevel(new UserLevel(userLevel));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userResult;
    }

    @Override
    public List<User> readAll() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_users");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String userLevel = resultSet.getString("user_level");
                User user = new User();
                user.setId(id);
                user.setLogin(login);
                user.setPassword(password);
                if (userLevel.equals(Level.ADMIN.get().toString())) {
                    user.setUserLevel(Level.ADMIN.get());
                } else if (userLevel.equals(Level.MANAGER.get().toString())) {
                    user.setUserLevel(Level.MANAGER.get());
                } else {
                    user.setUserLevel(Level.SIMPLE.get());
                }
                usersList.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }


}
