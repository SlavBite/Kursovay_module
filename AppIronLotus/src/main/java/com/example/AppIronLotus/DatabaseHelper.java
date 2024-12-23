package com.example.AppIronLotus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/appironlotus";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "qwerty";

    // Получение подключения к базе данных
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
    }

    // Проверка авторизации пользователя
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение ID пользователя по имени
    public static int getUserId(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getUserName(int userId) {
        String query = "SELECT username FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Возвращаем null, если имя пользователя не найдено
    }

    // Получаем список всех пользователей
    public static List<String> getAllUsers(int currentUserId) {
        List<String> users = new ArrayList<>();
        String query = "SELECT username FROM users WHERE id != ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, currentUserId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    // Получаем все сообщения между двумя пользователями
    public static List<String> getMessagesBetweenUsers(int userId1, int userId2) {
        List<String> messages = new ArrayList<>();
        String query = "SELECT sender_id, receiver_id, message, timestamp FROM messages " +
                "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                "ORDER BY timestamp ASC";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId1);
            statement.setInt(2, userId2);
            statement.setInt(3, userId2);
            statement.setInt(4, userId1);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String sender = resultSet.getInt("sender_id") == userId1 ? "You" : getUserName(resultSet.getInt("sender_id"));
                    String message = resultSet.getString("message");
                    String timestamp = resultSet.getString("timestamp");
                    messages.add(sender + " (" + timestamp + "): " + message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static int getUserIdByName(String username) {
        String url = "jdbc:mysql://localhost:3306/appironlotus";
        String dbUsername = "root";
        String dbPassword = "qwerty";
        int userId = -1;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
