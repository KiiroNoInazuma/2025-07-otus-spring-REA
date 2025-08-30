package ru.otus.hw02.config;

import lombok.Data;


@Data
public class AppProperties implements TestConfig, TestFileNameProvider {


    private int rightAnswersCountToPass;

    private String testFileName;

    public AppProperties(int rightAnswersCountToPass, String testFileName) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;
    }
}
