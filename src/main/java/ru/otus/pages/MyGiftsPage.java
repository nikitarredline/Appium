package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.GiftItem;
import ru.otus.components.GiftsContent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class MyGiftsPage extends AbsBasePage {

    private final GiftsContent giftsContent =
            new GiftsContent($(id("ru.otus.wishlist:id/gifts")));

    public MyGiftsPage assertNumberOfGifts(int value) {
        giftsContent
                .shouldBe(visible.because("Список списков подарков не виден на экране"))
                .assertSizeEqualTo(value);
        return this;
    }

    public MyGiftsPage assertGiftTitle(int index, String value) {
        getGiftItem(index).assertTitleEqualsTo(value);
        return this;
    }

    public MyGiftsPage assertGiftPrice(int index, String value) {
        getGiftItem(index).assertPriceEqualsTo(value);
        return this;
    }

    public MyGiftsPage assertGiftSubtitle(int index, String value) {
        getGiftItem(index).assertSubtitleEqualsTo(value);
        return this;
    }

    public void tapEditGift(int index) {
        getGiftItem(index).tapEdit();
    }

    public GiftItem getGiftItem(int index) {
        return giftsContent.get(index)
                .shouldBe(visible.because("Список подарков %d не виден на экране".formatted(index)));
    }

    public MyGiftsPage tapCreateGift() {
        giftsContent.tapCreate();
        return this;
    }

    public MyGiftsPage tapReservedGift(int index) {
        getGiftItem(index).tapReserved();
        return this;
    }

    public MyGiftsPage assertReservedState(int index, boolean expected) {
        getGiftItem(index).assertReserved(expected);
        return this;
    }
}
