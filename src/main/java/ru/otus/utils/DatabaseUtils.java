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
        }
    }

    @SneakyThrows
    public void prepareWishlist(String login, String title, String description) {
        String sql = "INSERT INTO wishlists (id, description, title, user_id) " +
                "VALUES (?, ?, ?, (SELECT id FROM users WHERE username = ?))";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, java.util.UUID.randomUUID());
            ps.setString(2, description);
            ps.setString(3, title);
            ps.setString(4, login);

            int rows = ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void prepareGift(String login, String name, String description, int price) {
        String sql = "INSERT INTO gifts (id, description, image_url, name, price, is_reserved, store_url, wish_id) " +
                "VALUES (?, ?, null, ?, ?, false, null, (SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)))";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, java.util.UUID.randomUUID());
            ps.setString(2, description);
            ps.setString(3, name);
            ps.setInt(4, price);
            ps.setString(5, login);

            int rows = ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void deleteUser(String username) {
        String sqlDeleteGifts = "DELETE FROM gifts WHERE wish_id = (SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?))";
        String sqlDeleteWishlists = "DELETE FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        String sqlDeleteUser = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(this.url, this.username, this.password)) {
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteGifts)) {
                ps.setString(1, username);
                int giftsDeleted = ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteWishlists)) {
                ps.setString(1, username);
                int wishlistsDeleted = ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteUser)) {
                ps.setString(1, username);
                int usersDeleted = ps.executeUpdate();
            }
        }
    }
}
