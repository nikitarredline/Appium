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
public class UsersTest {

    @Inject private LoginPage loginPage;
    @Inject private TestUserProvider userProvider;
    @Inject private DatabaseUtils databaseUtils;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private MyGiftsPage myGiftsPage;
    @Inject private UsersPage usersPage;
    @Inject private FilterPage filterPage;

    private final String WISHLIST_TITLE = "Новый год";
    private final String WISHLIST_DESCRIPTION = "К нам мчится, скоро все случится";
    private final String GIFT_TITLE = "Новый подарок";
    private final String GIFT_DESCRIPTION = "Нам дарят";

    @Test
    void changingReservationStatus() {
        TestUser user3 = userProvider.get("user3");
        TestUser user4 = userProvider.get("user4");
        databaseUtils.deleteWishlist(user4.getUsername());
        loginPage.login(user3.getUsername(), user3.getPassword());
        databaseUtils.prepareWishlist(user4.getUsername(), WISHLIST_TITLE, WISHLIST_DESCRIPTION);
        databaseUtils.prepareGift(user4.getUsername(), GIFT_TITLE, GIFT_DESCRIPTION, 500);
        usersPage
                .openUsers()
                .openFilter();
        filterPage
                .assertEditFilter("Фильтры")
                .editFilter(user4.getUsername());
        usersPage.selectUser(1);
        myWishlistsPage.tapTitleWishlist(1, WISHLIST_TITLE);
        myGiftsPage
                .tapReservedGift(1)
                .assertReservedState(1, true);
    }
}
