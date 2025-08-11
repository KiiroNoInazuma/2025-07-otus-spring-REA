package ru.otus.hw01.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw01.dao.QuestionDao;
import ru.otus.hw01.domain.Answer;
import ru.otus.hw01.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        try {
            List<Question> questions = questionDao.findAll();
            int correctAnswers = 0;

            for (Question question : questions) {
                ioService.printLine(question.text());

                // Выводим варианты ответов с нумерацией
                for (int i = 0; i < question.answers().size(); i++) {
                    ioService.printFormattedLine("%d. %s", i + 1, question.answers().get(i).text());
                }

                // Запрашиваем ответ пользователя
                int userAnswer = ioService.readIntWithPrompt(
                        "Enter your answer (1-" + question.answers().size() + "): ",
                        1, question.answers().size());

                // Проверяем правильность ответа
                boolean isCorrect = question.answers().get(userAnswer - 1).isCorrect();
                if (isCorrect) {
                    correctAnswers++;
                    ioService.printLine("Correct!\n");
                } else {
                    ioService.printLine("Incorrect!\n");
                }
            }

            // Выводим итоговый результат
            ioService.printFormattedLine("Test completed! Your score: %d/%d%n",
                    correctAnswers, questions.size());

        } catch (Exception e) {
            ioService.printLine("Error occurred while testing: " + e.getMessage());
        }
    }

}