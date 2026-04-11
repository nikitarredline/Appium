package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.BottomNavigationComponent;
import ru.otus.components.HeaderComponent;
import ru.otus.components.UsersListContent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class UsersPage extends AbsBasePage {

    private final BottomNavigationComponent bottomNavigationComponent = new BottomNavigationComponent($(id("android:id/content")));
    private final HeaderComponent header = new HeaderComponent($(id("android:id/content")));
    private final UsersListContent usersListContent =
            new UsersListContent($(id("ru.otus.wishlist:id/users")));

    public void tapFilterUsers() {
        header.tapFilter();
    }

    public UsersPage tapUsers() {
        bottomNavigationComponent.tapUsersButton();
        return this;
    }

    public UsersListContent getUsersItem(int index) {
        return usersListContent.get(index)
                .shouldBe(visible.because("Список пользователей %d не виден на экране".formatted(index)));
    }

    public UsersPage tapTitleUsername(int index, String value) {
        getUsersItem(index).tapUsername(value);
        return this;
    }
}
