package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.AndroidExtension;
import ru.otus.pages.EditWishlistPage;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;

@ExtendWith(AndroidExtension.class)
public class WishlistEditTest {

    @Inject private LoginPage loginPage;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private EditWishlistPage editWishlistPage;

    @Test
    void editWishlist() {
        loginPage.login("tonyp90", "12345678");
        String wishlistTitle = "Новый год";
        String newWishlistDescription = "К нам уже не мчатся";
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, "К нам мчится, скоро все случится")
                .tapEditWishlist(1);
        editWishlistPage
                .assertEditWishlistTitle("Изменить список желаний")
                .editDescription(newWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }
}
