package ru.otus.hw01.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw01.config.TestFileNameProvider;
import ru.otus.hw01.dao.dto.QuestionDto;
import ru.otus.hw01.domain.Question;
import ru.otus.hw01.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileNameProvider.getTestFileName())) {
            if (isNull(inputStream)) {
                throw new QuestionReadException(String.format("File not found %s",
                        fileNameProvider.getTestFileName()));
            }
            var questionDtos = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build()
                    .parse();
            return questionDtos.stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new QuestionReadException("Error while reading questions", e);
        }
    }
}