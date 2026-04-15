package ru.otus.pageobject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;

public class AbsPageObject {

    protected void shouldBeVisible(SelenideElement element, String message) {
        element.shouldBe(visible.because(message));
    }
}
