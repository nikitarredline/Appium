package ru.otus.factory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("unused")
public class AndroidDriverModule extends AbstractModule {

    @Provides
    private WebDriver webDriver(AndroidDriverFactory factory) {
        return factory.create();
    }

    @Provides
    @Singleton
    private Capabilities capabilities() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setDeviceName("emulator-5554");

        options.setAppPackage("ru.otus.wishlist");
        options.setAppActivity("ru.otus.wishlist.MainActivity");

        options.clearDeviceLogsOnStart();

        options.noReset();

        return options;
    }

//    @Provides
//    @Singleton
//    private Capabilities capabilities() {
//        UiAutomator2Options options = new UiAutomator2Options();
//
//        options.setPlatformName("Android");
//        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
//        options.setDeviceName("emulator-5554");
//
//        options.setApp("http://localhost:8080/wishlist.apk");
//        options.clearDeviceLogsOnStart();
//        options.fullReset();
//
//        return options;
//    }
}