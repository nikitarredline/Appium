package ru.otus.utils;

import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Singleton
public class LogcatManager {

//    @SneakyThrows
//    public void saveLogs(WebDriver driver, String filename) {
//        List<String> logs =
//                driver.manage().logs().get("logcat").getAll().stream().map(Objects::toString).toList();
//        Files.write(Paths.get(filename + ".log"), logs);
//    }

    @SneakyThrows
    public void saveLogs(String filename) {
        Process process = Runtime.getRuntime().exec("adb logcat -d");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        StringBuilder logs = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            logs.append(line).append("\n");
        }

        Files.writeString(Path.of(filename + ".log"), logs.toString());

        // очищаем логи после сохранения
        Runtime.getRuntime().exec("adb logcat -c");
    }
}
