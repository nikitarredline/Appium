package ru.otus.utils;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class DatabaseUtils {

    private final String url = System.getProperty("databaseUrl");
    private final String username = System.getProperty("databaseUsername");
    private final String password = System.getProperty("databasePassword");

    @SneakyThrows
    public void prepareWishlistDescription(String login, String description) {
        String sql = "UPDATE wishlists " +
                "SET description = ? " +
                "WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, description);
            ps.setString(2, login);
            int rows = ps.executeUpdate();
            System.out.println("Updated wishlists for user " + login + ", rows affected: " + rows);
        }
    }

    @SneakyThrows
    public void createUser(String login, String email, String password) {
        String sql = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (username) DO NOTHING";
        UUID userId = UUID.randomUUID(); // генерируем UUID для id
        try (Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, userId);       // UUID для id
            ps.setString(2, login);        // username
            ps.setString(3, email);        // email
            ps.setString(4, password);     // password
            int updated = ps.executeUpdate();
            System.out.println("Inserted user: " + login + ", id: " + userId + ", rows affected: " + updated);
        }
    }

    @SneakyThrows
    public void deleteUser(String username) {
        String sqlDeleteWishlists = "DELETE FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        String sqlDeleteUser = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(this.url, this.username, this.password)) {
            // Удаляем все вишлисты пользователя
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteWishlists)) {
                ps.setString(1, username);
                int wishlistsDeleted = ps.executeUpdate();
                System.out.println("Deleted wishlists for user " + username + ", rows affected: " + wishlistsDeleted);
            }

            // Удаляем пользователя
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteUser)) {
                ps.setString(1, username);
                int usersDeleted = ps.executeUpdate();
                System.out.println("Deleted user " + username + ", rows affected: " + usersDeleted);
            }
        }
    }
}
