package ru.otus.hw02.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw02.config.TestFileNameProvider;
import ru.otus.hw02.domain.Question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @TempDir
    private Path tempDir;


    @Test
    void indAllShouldReadQuestionsFromCsvFileCorrectly() throws IOException {
        Path testFile = tempDir.resolve("questions.csv");
        String csvContent = """
                text;answers
                "What is 2+2?";"3%false|4%true|5%false"
                "What is the capital of Russia?";"London%false|Moscow%true|Berlin%false"
                """;
        Files.writeString(testFile, csvContent);
        when(fileNameProvider.getTestFileName()).thenReturn(testFile.getFileName().toString());
        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
        assertEquals(2, questions.size());

        var firstQuestion = questions.getFirst();
        assertEquals("What is 2+2?", firstQuestion.text());
        assertEquals(3, firstQuestion.answers().size());
        assertEquals("4", firstQuestion.answers().get(1).text());
        assertTrue(firstQuestion.answers().get(1).isCorrect());

        Question secondQuestion = questions.get(1);
        assertEquals("What is the capital of Russia?", secondQuestion.text());
        assertEquals("Moscow", secondQuestion.answers().get(1).text());
        assertTrue(secondQuestion.answers().get(1).isCorrect());
    }

    @Test
    void findAllShouldThrowExceptionWhenFileNotFound() {
        when(fileNameProvider.getTestFileName()).thenReturn("unname.csv");
        assertThrows(RuntimeException.class, () -> csvQuestionDao.findAll());
    }
}