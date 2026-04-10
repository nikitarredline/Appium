package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class FilterPage extends AbsBasePage {

    private final SelenideElement title =
            $(id("ru.otus.wishlist:id/filter_title"))
                    .as("Заголовок формы фильтра");
    private final SelenideElement usernameField =
            $(id("ru.otus.wishlist:id/username_input"))
                    .as("Поле ввода имени пользователя");
    private final SelenideElement applyButton =
            $(id("ru.otus.wishlist:id/apply_button"))
                    .as("Кнопка применить");

    public FilterPage assertEditFilter(String expected) {
        title
                .shouldBe(visible.because("Заголовок не виден на экране"))
                .shouldHave(text(expected).because("Неверный текст заголовка"));
        return this;
    }

    public void editFilter(String username) {
        usernameField
                .shouldBe(visible.because("Поле ввода имени пользователя не видно на экране"))
                .clear();
        usernameField.sendKeys(username);
        applyButton
                .shouldBe(visible.because("Кнопка применить не видна на экране"))
                .click();
    }
}
