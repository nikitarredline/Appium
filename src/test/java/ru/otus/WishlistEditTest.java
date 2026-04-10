package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.AndroidExtension;
import ru.otus.pages.*;
import ru.otus.utils.DatabaseUtils;

@ExtendWith(AndroidExtension.class)
public class WishlistEditTest {

    @Inject private LoginPage loginPage;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private EditWishlistPage editWishlistPage;
    @Inject private CreateWishlistPage createWishlistPage;
    @Inject private MyGiftsPage myGiftsPage;
    @Inject private EditGiftPage editGiftPage;
    @Inject private CreateGiftPage createGiftPage;
    @Inject private UsersPage usersPage;
    @Inject private FilterPage filterPage;

    String wishlistTitle = "Новый год";
    String wishlistDescription = "К нам мчится, скоро все случится";
    String giftTitle = "Новый подарок";
    String giftDescription = "Нам дарят";
    private final DatabaseUtils dbUtils = new DatabaseUtils();
    String testUser = new java.util.Random().ints(5, 0, 26)
            .mapToObj(i -> "" + (char)('a' + i))
            .reduce("", String::concat) + "90";
    String testEmail = new java.util.Random().ints(10, 0, 26)
            .mapToObj(i -> "" + (char)('a' + i))
            .reduce("", String::concat) + "@mail.ru";

    @BeforeEach
    void setup() {
        dbUtils.createUser(testUser, testEmail, "$2a$10$UTECxqfTnyDk1hDggh.5t.MiOeNj4WLlFeWGGL/vvMtG.kvZDCOta");
    }

    @Test
    void createAndEditWishlist() {
        loginPage.login(testUser, "12345678");
        String newWishlistDescription = "К нам уже не мчатся";
        myWishlistsPage
                .tapCreateWishlist();
        createWishlistPage
                .assertCreateWishlistTitle("Новый список желаний")
                .createWishlist(wishlistTitle, wishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, wishlistDescription)
                .tapEditWishlist(1);
        editWishlistPage
                .assertEditWishlistTitle("Изменить список желаний")
                .editDescription(newWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }

    @Test
    void createAndEditGift() {
        String giftPrice = "555";
        String newGiftDescription = "Нам уже не подарят";
        dbUtils.prepareWishlist(testUser, wishlistTitle, wishlistDescription);
        loginPage.login(testUser, "12345678");
        myWishlistsPage
                .tapTitleWishlist(1, wishlistTitle);
        myGiftsPage
                .tapCreateGift();
        createGiftPage
                .assertCreateGiftTitle("Новый подарок")
                .createGift(giftTitle, giftPrice, giftDescription);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, giftTitle)
                .assertGiftPrice(1, giftPrice)
                .assertGiftSubtitle(1, giftDescription)
                .tapEditGift(1);
        editGiftPage
                .assertEditGiftTitle("Изменить подарок")
                .editDescription(newGiftDescription);
        myGiftsPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, giftTitle)
                .assertGiftSubtitle(1, newGiftDescription);
    }

    @Test
    void changingReservationStatus() {
        dbUtils.prepareWishlist(testUser, wishlistTitle, wishlistDescription);
        dbUtils.prepareGift(testUser, giftTitle, giftDescription, 500);
        String username = testUser;
        String testUser = new java.util.Random().ints(5, 0, 26)
                .mapToObj(i -> "" + (char)('a' + i))
                .reduce("", String::concat) + "90";
        String testEmail = new java.util.Random().ints(10, 0, 26)
                .mapToObj(i -> "" + (char)('a' + i))
                .reduce("", String::concat) + "@mail.ru";
        dbUtils.createUser(testUser, testEmail, "$2a$10$UTECxqfTnyDk1hDggh.5t.MiOeNj4WLlFeWGGL/vvMtG.kvZDCOta");
        loginPage.login(testUser, "12345678");
        usersPage
                .tapUsers()
                .tapFilter();
        filterPage
                .assertEditFilter("Фильтры")
                .editFilter(username);
        usersPage
                .tapUsername();
        myWishlistsPage
                .tapTitleWishlist(1, wishlistTitle);
        myGiftsPage
                .tapReservedGift(1)
                .assertReservedState(1, true);
        dbUtils.deleteUser(testUser);
    }

    @AfterEach
    void cleanup() {
        dbUtils.deleteUser(testUser);
    }
}
