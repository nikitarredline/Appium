package ru.otus.components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import ru.otus.pageobject.AbsPageObject;

@SuppressWarnings("unchecked")
public abstract class AbsComponent<T extends AbsComponent<T>> extends AbsPageObject {

    protected final SelenideElement root;

    protected AbsComponent(SelenideElement root) {
        this.root = root;
    }

    public T shouldBe(WebElementCondition... conditions) {
        root.shouldBe(conditions);
        return (T) this;
    }
}