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
public class UsersTest {

    @Inject private LoginPage loginPage;
    @Inject private DatabaseUtils databaseUtils;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private MyGiftsPage myGiftsPage;
    @Inject private UsersPage usersPage;
    @Inject private FilterPage filterPage;

    private static final String WISHLIST_TITLE = "Новый год";
    private static final String WISHLIST_DESCRIPTION = "К нам мчится, скоро все случится";
    private static final String GIFT_TITLE = "Новый подарок";
    private static final String GIFT_DESCRIPTION = "Нам дарят";

    @BeforeEach
    void setup() {
        loginPage.login(TestUsers.USER_1.username, TestUsers.USER_1.password);
    }

    @Test
    void changingReservationStatus() {
        databaseUtils.prepareWishlist(TestUsers.USER_2.username, WISHLIST_TITLE, WISHLIST_DESCRIPTION);
        databaseUtils.prepareGift(TestUsers.USER_2.username, GIFT_TITLE, GIFT_DESCRIPTION, 500);
        usersPage
                .tapUsers()
                .tapFilterUsers();
        filterPage
                .assertEditFilter("Фильтры")
                .editFilter(TestUsers.USER_2.username);
        usersPage.tapTitleUsername(1, TestUsers.USER_2.username);
        myWishlistsPage.tapTitleWishlist(1, WISHLIST_TITLE);
        myGiftsPage
                .tapReservedGift(1)
                .assertReservedState(1, true);
    }

    @AfterEach
    void cleanup() {
        databaseUtils.deleteWishlist(TestUsers.USER_2.username);
    }
}
