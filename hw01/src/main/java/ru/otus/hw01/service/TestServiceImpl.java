package ru.otus.hw01.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw01.dao.QuestionDao;
import ru.otus.hw01.domain.Question;

import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        try {
            int correct = 0;
            for (Question q : questionDao.findAll()) {
                printQuestion(q);
                correct += checkAnswer(q) ? 1 : 0;
            }
            ioService.printFormattedLine("Test completed! Score: %d/%d%n", correct, questionDao.findAll().size());
        } catch (Exception e) {
            ioService.printLine("Error: " + e.getMessage());
        }
    }

    private void printQuestion(Question q) {
        ioService.printLine("\n" + q.text());
        IntStream.range(0, q.answers().size())
                .forEach(i -> ioService.printFormattedLine("%d. %s", i + 1, q.answers().get(i).text()));
    }

    private boolean checkAnswer(Question q) {
        int choice = ioService.readIntWithPrompt("Your answer (1-" + q.answers().size() + "): ", 1, q.answers().size());
        boolean correct = q.answers().get(choice - 1).isCorrect();
        ioService.printLine(correct ? "Correct!" : "Incorrect!");
        return correct;
    }

}