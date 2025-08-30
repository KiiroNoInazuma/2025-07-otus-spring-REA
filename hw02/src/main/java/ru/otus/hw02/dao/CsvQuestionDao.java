package ru.otus.hw02.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw02.config.TestFileNameProvider;
import ru.otus.hw02.dao.dto.QuestionDto;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (var inputStream = Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(fileNameProvider.getTestFileName()))) {
            return questionDtoListFromCsvBuilder(inputStream).stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (IOException e) {
            throw new QuestionReadException("Error while reading questions", e);
        }
    }

    private List<QuestionDto> questionDtoListFromCsvBuilder(InputStream inputStream) {
        return questionDtoCsvToBeanBuild(inputStream)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withSkipLines(1)
                .build()
                .parse();
    }

    private CsvToBeanBuilder<QuestionDto> questionDtoCsvToBeanBuild(InputStream inputStream) {
        return new CsvToBeanBuilder<>(new InputStreamReader(inputStream));
    }

}
