package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class UsersListContent extends AbsComponent<UsersListContent> {

    private final ElementsCollection items =
            root.$$(id("ru.otus.wishlist:id/user_item"))
                    .as("Список пользователей");
    private final SelenideElement username =
            root.$(id("ru.otus.wishlist:id/username"))
                    .as("Выбор пользователя в списке пользователей");

    public UsersListContent(SelenideElement root) {
        super(root);
    }

    public UsersListContent get(int index) {
        return new UsersListContent(items.get(index - 1));
    }

    public void tapUsername(String value) {
        username.shouldBe(visible.because("Пользователь не виден в списке пользователей"))
                .click();
    }
}
