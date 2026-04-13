package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.AndroidExtension;
import ru.otus.pages.*;
import ru.otus.testdata.TestUser;
import ru.otus.testdata.TestUserProvider;
import ru.otus.utils.DatabaseUtils;

@ExtendWith(AndroidExtension.class)
public class WishlistsTest {

    @Inject private LoginPage loginPage;
    @Inject private TestUserProvider userProvider;
    @Inject private DatabaseUtils databaseUtils;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private FieldsWishlistPage fieldsWishlistPage;
    @Inject private MyGiftsPage myGiftsPage;
    @Inject private FieldsGiftPage fieldsGiftPage;

    private final String WISHLIST_TITLE = "Новый год";
    private final String WISHLIST_DESCRIPTION = "К нам мчится, скоро все случится";
    private final String GIFT_TITLE = "Новый подарок";
    private final String GIFT_DESCRIPTION = "Нам дарят";

    @Test
    void createAndEditWishlist() {
        TestUser user1 = userProvider.get("user1");
        databaseUtils.deleteWishlist(user1.getUsername());
        loginPage.login(user1.getUsername(), user1.getPassword());
        String newWishlistDescription = "К нам уже не мчатся";
        myWishlistsPage.tapCreateWishlist();
        fieldsWishlistPage
                .assertTitle("Новый список желаний")
                .create(WISHLIST_TITLE, WISHLIST_DESCRIPTION);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, WISHLIST_TITLE)
                .assertWishlistSubtitle(1, WISHLIST_DESCRIPTION)
                .tapEditWishlist(1);
        fieldsWishlistPage
                .assertTitle("Изменить список желаний")
                .editDescription(newWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, WISHLIST_TITLE)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }

    @Test
    void createAndEditGift() {
        TestUser user2 = userProvider.get("user2");
        databaseUtils.deleteWishlist(user2.getUsername());
        loginPage.login(user2.getUsername(), user2.getPassword());
        String giftPrice = "555";
        String newGiftDescription = "Нам уже не подарят";
        databaseUtils.prepareWishlist(user2.getUsername(), WISHLIST_TITLE, WISHLIST_DESCRIPTION);
        myWishlistsPage.tapTitleWishlist(1, WISHLIST_TITLE);
        myGiftsPage.tapCreateGift();
        fieldsGiftPage
                .assertTitle("Новый подарок")
                .create(GIFT_TITLE, giftPrice, GIFT_DESCRIPTION);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, GIFT_TITLE)
                .assertGiftPrice(1, giftPrice)
                .assertGiftSubtitle(1, GIFT_DESCRIPTION)
                .tapEditGift(1);
        fieldsGiftPage
                .assertTitle("Изменить подарок")
                .editDescription(newGiftDescription);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, GIFT_TITLE)
                .assertGiftSubtitle(1, newGiftDescription);
    }
}