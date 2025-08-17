package ru.otus.hw01.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final IOService ioService;

    @Override
    public void run() {
        ioService.printLine("Welcome to Student Testing System!");
        ioService.printLine("Please enter your first and last name:");
        String studentName = ioService.readLine();
        ioService.printFormattedLine("Hello, %s! Let's start the test.%n", studentName);
        testService.executeTest();
        ioService.printLine("Test completed. Thank you!");
    }
}