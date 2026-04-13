package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.UsersListContent;

import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class UsersPage extends AbsBasePage {

    private final UsersListContent usersList =
            new UsersListContent($(id("ru.otus.wishlist:id/users")));

    public UsersPage openUsers() {
        bottomNav.tapUsers();
        return this;
    }

    public void openFilter() {
        header.tapFilter();
    }

    public void selectUser(int index) {
        usersList.tapUser(index);
    }
}