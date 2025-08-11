package ru.otus.hw01.service;

import java.io.PrintStream;
import java.util.Scanner;

public class StreamsIOService implements IOService {
    private final PrintStream printStream;
    private final Scanner scanner;

    public StreamsIOService(PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void printLine(String s) {
        printStream.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        printStream.printf(s + "%n", args);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public int readIntWithPrompt(String prompt, int min, int max) {
        while (true) {
            try {
                printLine(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                printFormattedLine("Please enter a number between %d and %d", min, max);
            } catch (NumberFormatException e) {
                printLine("Invalid input. Please enter a number.");
            }
        }
    }
}