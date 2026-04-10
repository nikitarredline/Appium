package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class CreateGiftPage {

    private final SelenideElement title =
            $(id("ru.otus.wishlist:id/gift_edit_title"))
                    .as("Заголовок формы создания списка подарков");
    private final SelenideElement giftTitleInputField =
            $(id("ru.otus.wishlist:id/name_input"))
                    .as("Поле ввода заголовка списка подарков");
    private final SelenideElement giftPriceInputField =
            $(id("ru.otus.wishlist:id/price_input"))
                    .as("Поле ввода цены подарков");
    private final SelenideElement giftDescriptionInputField =
            $(id("ru.otus.wishlist:id/description_input"))
                    .as("Поле ввода подзаголовка списка подарков");
    private final SelenideElement saveButton =
            $(id("ru.otus.wishlist:id/save_button"))
                    .as("Кнопка сохранения списка подарков");

    public CreateGiftPage assertCreateGiftTitle(String expected) {
        title
                .shouldBe(visible.because("Заголовок не виден на экране"))
                .shouldHave(text(expected).because("Неверный текст заголовка"));
        return this;
    }

    public void createGift(String title, String price, String description) {
        giftTitleInputField
                .shouldBe(visible.because("Поле ввода заголовка не видно на экране"))
                .clear();
        giftTitleInputField.sendKeys(title);
        giftPriceInputField
                .shouldBe(visible.because("Поле ввода цены не видно на экране"))
                .clear();
        giftPriceInputField.sendKeys(price);
        giftDescriptionInputField
                .shouldBe(visible.because("Поле ввода подзаголовка не видно на экране"))
                .clear();
        giftDescriptionInputField.sendKeys(description);
        saveButton
                .shouldBe(visible.because("Кнопка сохранения не видна на экране"))
                .click();
    }
}
