package ru.otus.emulator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Emulator {

    ANDROID(4723);

    private final int port;
}