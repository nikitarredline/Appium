package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class CreateWishlistPage extends AbsBasePage {

    private final SelenideElement title =
            $(id("ru.otus.wishlist:id/wishlist_edit_title"))
                    .as("Заголовок формы создания списка желаний");
    private final SelenideElement wishlistTitleInputField =
            $(id("ru.otus.wishlist:id/title_input"))
                    .as("Поле ввода заголовка списка желаний");
    private final SelenideElement wishlistDescriptionInputField =
            $(id("ru.otus.wishlist:id/description_input"))
                    .as("Поле ввода подзаголовка списка желаний");
    private final SelenideElement saveButton =
            $(id("ru.otus.wishlist:id/save_button"))
                    .as("Кнопка сохранения списка желаний");

    public CreateWishlistPage assertCreateWishlistTitle(String expected) {
        title
                .shouldBe(visible.because("Заголовок не виден на экране"))
                .shouldHave(text(expected).because("Неверный текст заголовка"));
        return this;
    }

    public void createWishlist(String title, String description) {
        wishlistTitleInputField
                .shouldBe(visible.because("Поле ввода заголовка не видно на экране"))
                .clear();
        wishlistTitleInputField.sendKeys(title);
        wishlistDescriptionInputField
                .shouldBe(visible.because("Поле ввода подзаголовка не видно на экране"))
                .clear();
        wishlistDescriptionInputField.sendKeys(description);
        saveButton
                .shouldBe(visible.because("Кнопка сохранения не видна на экране"))
                .click();
    }
}
