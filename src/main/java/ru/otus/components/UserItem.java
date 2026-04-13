package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class UserItem extends AbsComponent<UserItem> {

    private final SelenideElement username =
            root.$(id("ru.otus.wishlist:id/username"))
                    .as("Имя пользователя");

    public UserItem(SelenideElement root) {
        super(root);
    }

    public void click() {
        username.shouldBe(visible).click();
    }

    public String getUsername() {
        return username.getText();
    }
}
