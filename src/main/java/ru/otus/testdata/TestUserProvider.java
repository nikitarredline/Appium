package ru.otus.testdata;

import com.google.inject.Singleton;
import java.util.Map;

@Singleton
public class TestUserProvider {

    private final Map<String, TestUser> users = Map.of(
            "user1", new TestUser("redline1", "12345678"),
            "user2", new TestUser("redline2", "12345678"),
            "user3", new TestUser("redline3", "12345678"),
            "user4", new TestUser("redline4", "12345678")
    );

    public TestUser get(String key) {
        TestUser user = users.get(key);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + key);
        }
        return user;
    }
}
