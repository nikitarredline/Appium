package ru.otus.components;

import com.codeborne.selenide.SelenideElement;
import ru.otus.pages.UsersPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class BottomNavigationComponent extends AbsComponent<BottomNavigationComponent> {

    private final SelenideElement usersButton =
            $(id("ru.otus.wishlist:id/users_menu"))
                    .as("Кнопка пользователи");

    public BottomNavigationComponent(SelenideElement root) {
        super(root);
    }

    public BottomNavigationComponent tapUsersButton() {
        usersButton.shouldBe(visible.because("Кнопка пользователи не видна"))
                .click();
        return this;
    }
}
