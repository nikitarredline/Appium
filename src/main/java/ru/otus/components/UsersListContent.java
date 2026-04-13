package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static io.appium.java_client.AppiumBy.id;

public class UsersListContent extends AbsComponent<UsersListContent> {

    private final ElementsCollection items =
            root.$$(id("ru.otus.wishlist:id/user_item"))
                    .as("Список пользователей");

    public UsersListContent(SelenideElement root) {
        super(root);
    }

    public UserItem get(int index) {
        return new UserItem(items.get(index - 1));
    }

    public void tapUser(int index) {
        get(index).click();
    }
}