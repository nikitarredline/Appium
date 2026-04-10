package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class GiftsContent extends AbsComponent<GiftsContent>{

    private final ElementsCollection items =
            root.$$(id("ru.otus.wishlist:id/gift_item"))
                    .as("Списки подарков");
    private final SelenideElement createButton =
            $(id("ru.otus.wishlist:id/add_button"))
                    .as("Кнопка создания списка подарков");

    public GiftsContent(SelenideElement root) {
        super(root);
    }

    public GiftItem get(int index) {
        return new GiftItem(items.get(index - 1));
    }

    public void assertSizeEqualTo(int expected) {
        items.shouldHave(
                size(expected).because("Неожидаемое количество списков подарков"));
    }

    public void tapCreate() {
        createButton.shouldBe(visible.because("Кнопка создания списка подарков не видна"))
                .click();
    }
}
