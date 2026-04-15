package ru.otus.pages;

import ru.otus.components.BottomNavComponent;
import ru.otus.components.HeaderComponent;
import ru.otus.pageobject.AbsPageObject;

import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;

public abstract class AbsBasePage extends AbsPageObject {

    protected final HeaderComponent header =
            new HeaderComponent($(id("ru.otus.wishlist:id/top_app_bar_layout")));

    protected final BottomNavComponent bottomNav =
            new BottomNavComponent($(id("ru.otus.wishlist:id/bottom_navigation")));
}