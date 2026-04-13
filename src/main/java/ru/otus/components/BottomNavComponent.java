package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class BottomNavComponent extends AbsComponent<BottomNavComponent> {

    private final SelenideElement usersButton =
            root.$(id("ru.otus.wishlist:id/users_menu"))
                    .as("Кнопка пользователи");

    public BottomNavComponent(SelenideElement root) {
        super(root);
    }

    public void tapUsers() {
        usersButton.shouldBe(visible.because("Кнопка пользователи не видна"))
                .click();
    }
}
