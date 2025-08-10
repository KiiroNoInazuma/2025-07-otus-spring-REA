package ru.otus.hw01;

import org.springframework.context.ApplicationContext;
import ru.otus.hw01.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {

        //Прописать бины в spring-context.xml и создать контекст на его основе
        ApplicationContext context = null;
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}