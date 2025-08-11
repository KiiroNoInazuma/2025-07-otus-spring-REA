package ru.otus.hw01.service;

public interface IOService {
    void printLine(String s);

    void printFormattedLine(String s, Object... args);

    String readLine();

    int readIntWithPrompt(String prompt, int min, int max);
}
