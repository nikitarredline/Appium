package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class UsersPage extends AbsBasePage {

    private final SelenideElement usersButton =
            $(id("ru.otus.wishlist:id/users_menu"))
                    .as("Кнопка пользователи");
    private final SelenideElement filterButton =
            $(id("ru.otus.wishlist:id/filter"))
                    .as("Кнопка фильтр");
    private final SelenideElement username =
            $(id("ru.otus.wishlist:id/username"))
                    .as("Выбор пользователя в списке пользователей");

    public UsersPage tapUsers() {
        usersButton.shouldBe(visible.because("Кнопка пользователи не видна"))
                .click();
        return this;
    }

    public void tapFilter() {
        filterButton.shouldBe(visible.because("Кнопка фильтр не видна"))
                .click();
    }

    public void tapUsername() {
        username.shouldBe(visible.because("Кнопка фильтр не видна"))
                .click();
    }
}
