package ru.otus.hw02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.Answer;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.domain.Student;
import ru.otus.hw02.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = askQuestionAndGetAnswer(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean askQuestionAndGetAnswer(Question question) {
        ioService.printFormattedLine("Question: %s", question.text());
        List<Answer> answers = question.answers();

        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            ioService.printFormattedLine("%d. %s", i + 1, answer.text());
        }

        int userAnswer = ioService.readIntForRangeWithPrompt(
                1,
                answers.size(),
                "Please enter the number of your answer: ",
                String.format("Please enter a number between 1 and %d", answers.size())
        );
        return answers.get(userAnswer - 1).isCorrect();
    }
}
