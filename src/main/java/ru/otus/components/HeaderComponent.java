package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class HeaderComponent extends AbsComponent<HeaderComponent> {

    private final SelenideElement filterButton =
            $(id("ru.otus.wishlist:id/filter"))
                    .as("Кнопка фильтр");

    public HeaderComponent(SelenideElement root) {
        super(root);
    }

    public void tapFilter() {
        filterButton.shouldBe(visible.because("Кнопка фильтр не видна"))
                .click();
    }
}
