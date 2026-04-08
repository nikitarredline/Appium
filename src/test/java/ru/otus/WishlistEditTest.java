package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.AndroidExtension;
import ru.otus.pages.CreateWishlistsPage;
import ru.otus.pages.EditWishlistPage;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;
import ru.otus.utils.DatabaseUtils;

@ExtendWith(AndroidExtension.class)
public class WishlistEditTest {

    @Inject private LoginPage loginPage;
    @Inject private MyWishlistsPage myWishlistsPage;
    @Inject private EditWishlistPage editWishlistPage;
    @Inject private CreateWishlistsPage createWishlistsPage;

    private final DatabaseUtils dbUtils = new DatabaseUtils();
    String testUser = new java.util.Random().ints(5, 0, 26)
            .mapToObj(i -> "" + (char)('a' + i))
            .reduce("", String::concat) + "90";

    @BeforeEach
    void setup() {
        System.out.println("URL: " + System.getProperty("databaseUrl"));
        System.out.println("User: " + System.getProperty("databaseUsername"));
        System.out.println("Password: " + System.getProperty("databasePassword"));

        String testEmail = new java.util.Random().ints(10, 0, 26)
                .mapToObj(i -> "" + (char)('a' + i))
                .reduce("", String::concat) + "@mail.ru";
        dbUtils.createUser(testUser, testEmail, "$2a$10$UTECxqfTnyDk1hDggh.5t.MiOeNj4WLlFeWGGL/vvMtG.kvZDCOta");
        dbUtils.prepareWishlistDescription(testUser, "К нам мчится, скоро все случится");
    }

    @Test
    void createAndEditWishlist() {
        loginPage.login(testUser, "12345678");
        String wishlistTitle = "Новый год";
        String oldWishlistDescription = "К нам мчится, скоро все случится";
        String newWishlistDescription = "К нам уже не мчатся";
        myWishlistsPage
                .tapCreateWishlist();
        createWishlistsPage
                .assertCreateWishlistTitle("Новый список желаний")
                .createWishlist(wishlistTitle, oldWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, oldWishlistDescription)
                .tapEditWishlist(1);
        editWishlistPage
                .assertEditWishlistTitle("Изменить список желаний")
                .editDescription(newWishlistDescription);
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }

    @AfterEach
    void cleanup() {
        dbUtils.deleteUser(testUser);
    }
}
