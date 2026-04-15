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

    private final String wishlistTitle = "Новый год";
    private final String wishlistDescription = "К нам мчится, скоро все случится";
    private final String giftTitle = "Новый подарок";
    private final String giftDescription = "Нам дарят";

    @Test
    void createAndEditWishlist() {
        TestUser user1 = userProvider.get("user1");
        databaseUtils.deleteWishlist(user1.getUsername());
        loginPage.login(user1.getUsername(), user1.getPassword());
        String newWishlistDescription = "К нам уже не мчатся";
        myWishlistsPage.tapCreateWishlist();
        fieldsWishlistPage
                .assertTitle("Новый список желаний")
                .create(wishlistTitle, wishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, wishlistDescription)
                .tapEditWishlist(1);
        fieldsWishlistPage
                .assertTitle("Изменить список желаний")
                .editDescription(newWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }

    @Test
    void createAndEditGift() {
        TestUser user2 = userProvider.get("user2");
        databaseUtils.deleteWishlist(user2.getUsername());
        loginPage.login(user2.getUsername(), user2.getPassword());
        String giftPrice = "555";
        String newGiftDescription = "Нам уже не подарят";
        databaseUtils.prepareWishlist(user2.getUsername(), wishlistTitle, wishlistDescription);
        myWishlistsPage.tapTitleWishlist(1, wishlistTitle);
        myGiftsPage.tapCreateGift();
        fieldsGiftPage
                .assertTitle("Новый подарок")
                .create(giftTitle, giftPrice, giftDescription);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, giftTitle)
                .assertGiftPrice(1, giftPrice)
                .assertGiftSubtitle(1, giftDescription)
                .tapEditGift(1);
        fieldsGiftPage
                .assertTitle("Изменить подарок")
                .editDescription(newGiftDescription);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, giftTitle)
                .assertGiftSubtitle(1, newGiftDescription);
    }
}