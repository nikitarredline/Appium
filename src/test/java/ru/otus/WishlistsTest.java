package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.AndroidExtension;
import ru.otus.pages.*;
import ru.otus.testdata.TestUsers;
import ru.otus.utils.DatabaseUtils;

@ExtendWith(AndroidExtension.class)
public class WishlistsTest {

    @Inject private LoginPage loginPage;
    @Inject private DatabaseUtils databaseUtils;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private FieldsWishlistPage fieldsWishlistPage;
    @Inject private MyGiftsPage myGiftsPage;
    @Inject private FieldsGiftPage fieldsGiftPage;

    private static final String WISHLIST_TITLE = "Новый год";
    private static final String WISHLIST_DESCRIPTION = "К нам мчится, скоро все случится";
    private static final String GIFT_TITLE = "Новый подарок";
    private static final String GIFT_DESCRIPTION = "Нам дарят";

    @BeforeEach
    void setup() {
        loginPage.login(TestUsers.USER_1.username, TestUsers.USER_1.password);
    }

    @Test
    void createAndEditWishlist() {
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
        String giftPrice = "555";
        String newGiftDescription = "Нам уже не подарят";
        databaseUtils.prepareWishlist(TestUsers.USER_1.username, WISHLIST_TITLE, WISHLIST_DESCRIPTION);
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

    @AfterEach
    void cleanup() {
        databaseUtils.deleteWishlist(TestUsers.USER_1.username);
    }
}