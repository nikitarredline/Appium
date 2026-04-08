package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public class WishlistsContent extends AbsComponent<WishlistsContent> {

    private final ElementsCollection items =
            root.$$(id("ru.otus.wishlist:id/wishlist_item"))
                    .as("Списки желаний");
    private final SelenideElement createButton =
            $(id("ru.otus.wishlist:id/add_button"))
                    .as("Кнопка создания списка желаний");

    public WishlistsContent(SelenideElement root) {
        super(root);
    }

    public WishlistItem get(int index) {
        return new WishlistItem(items.get(index - 1));
    }

    public void assertSizeEqualTo(int expected) {
        items.shouldHave(
                size(expected).because("Неожидаемое количество списков желаний"));
    }

    public void tapCreateWishlist() {
        createButton
                .shouldBe(visible.because("Кнопка создания списка желаний не видна"))
                .click();
    }
}
