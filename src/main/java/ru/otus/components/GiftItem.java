package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class GiftItem extends AbsComponent<GiftItem> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/title"))
                    .as("Заголовок списка подарков");
    private final SelenideElement price =
            root.$(id("ru.otus.wishlist:id/price"))
                    .as("Цена списка подарков");
    private final SelenideElement subtitile =
            root.$(id("ru.otus.wishlist:id/subtitle"))
                    .as("Подзаголовок списка подарков");
    private final SelenideElement editButton =
            root.$(id("ru.otus.wishlist:id/edit_button"))
                    .as("Кнопка редактирования списка подарков");
    private final SelenideElement reservedSwitch =
            root.$(id("ru.otus.wishlist:id/reserved"))
                    .as("Переключатель резервирования списка подарков");

    public GiftItem(SelenideElement root) {
        super(root);
    }

    public void assertTitleEqualsTo(String value) {
        title.shouldHave(
                text(value).because("Заголовок списка подарков не совпадает с ожидаемым"));
    }

    public void assertPriceEqualsTo(String value) {
        price.shouldHave(
                text(value).because("Цена подарков не совпадает с ожидаемой"));
    }

    public void assertSubtitleEqualsTo(String value) {
        subtitile.shouldHave(
                text(value).because("Подзаголовок списка подарков не совпадает с ожидаемым"));
    }

    public void tapEdit() {
        editButton.shouldBe(visible.because("Кнопка редактирования списка подарков не видна на экране"))
                .click();
    }

    public void tapReserved() {
        reservedSwitch.shouldBe(visible.because("Переключатель резервирования списка подарков не виден на экране"))
                .click();
    }

    public void assertReserved(boolean expected) {
        reservedSwitch.shouldBe(visible);
        boolean actual = Boolean.parseBoolean(reservedSwitch.getAttribute("checked"));
        if (actual != expected) {
            throw new AssertionError(
                    "Ожидалось: " + expected + ", но было: " + actual
            );
        }
    }
}
