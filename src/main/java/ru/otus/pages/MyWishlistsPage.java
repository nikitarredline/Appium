package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.WishlistItem;
import ru.otus.components.WishlistsContent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class MyWishlistsPage extends AbsBasePage {

    private final WishlistsContent wishlistsContent =
            new WishlistsContent($(id("ru.otus.wishlist:id/wishlists")));

    public MyWishlistsPage assertNumberOfWishlists(int value) {
        wishlistsContent
                .shouldBe(visible.because("Список списков желаний не виден на экране"))
                .assertSizeEqualTo(value);
        return this;
    }

    public MyWishlistsPage assertWishlistTitle(int index, String value) {
        getWishlistItem(index).assertTitleEqualsTo(value);
        return this;
    }

    public MyWishlistsPage assertWishlistSubtitle(int index, String value) {
        getWishlistItem(index).assertSubtitleEqualsTo(value);
        return this;
    }

    public void tapEditWishlist(int index) {
        getWishlistItem(index).tapEdit();
    }

    public WishlistItem getWishlistItem(int index) {
        return wishlistsContent.get(index)
                .shouldBe(visible.because("Список желаний %d не виден на экране".formatted(index)));
    }

    public MyWishlistsPage tapCreateWishlist() {
        wishlistsContent.tapCreate();
        return this;
    }

    public MyWishlistsPage tapTitleWishlist(int index, String value) {
        getWishlistItem(index).tapTitle(value);
        return this;
    }
}
