package com.myenglish.service;

import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.kafka.logger.LoggerProducerWithoutKafkaConfig;
import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
import com.myenglish.model.WordBuilder;
import com.myenglish.service.config.ServiceConfig;
import com.myenglish.service.filters.WordsBase;
import com.myenglish.testdb.TestH2DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class, ServiceConfig.class, TestH2DB.class, DaoHibernateConfig.class,
        LoggerProducerWithoutKafkaConfig.class})
@Transactional
@ActiveProfiles("withoutKafka")
public class WordServiceImplIT {

    @Autowired
    WordService wordService;

    @Test
    public void testSetupApp() {
        Assertions.assertNotNull(wordService);
    }

    @Test
    public void getFourRandomWordsTest() {
        FourWordsDto dto = wordService.getFourRandomWords();
        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getCorrectWord());
        Assertions.assertNotNull(dto.getFourRandomWords());
        Assertions.assertTrue(dto.getFourRandomWords().contains(dto.getCorrectWord()));
    }

    @Test
    public void getFourRandomWordsByDateFromTest() {
        LocalDate date = LocalDate.of(2021, 10, 11);
        FourWordsDto dto = wordService.getFourRandomWordsByDateFrom(date);
        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getCorrectWord());
        Assertions.assertNotNull(dto.getFourRandomWords());
        Assertions.assertTrue(dto.getFourRandomWords().contains(dto.getCorrectWord()));
        for (Word word : dto.getFourRandomWords()) {
            Assertions.assertTrue(date.isBefore(word.getDateOfRegistry()));
        }
    }

    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void writeSavedWordToTheFileTest() {
        String result = "('english', 'russian', 'description', '2020-10-01'),";
        Word word = WordBuilder.create().withEnglish("english")
                .withRussian("russian").withDescription("description")
                .withDateOfRegistry(LocalDate.of(2020, 10, 1))
                .build();
        File file = new File("src/test/resources/testSavedWords.txt");
        wordService.writeSavedWordToTheFile(word, file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String readSpring = reader.readLine();
                Assertions.assertNotNull(readSpring);
                Assertions.assertEquals(result, readSpring);
            }
            file.deleteOnExit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TestWordsBase implements WordsBase<Integer> {
    private Map<Word, Integer> base;

    public TestWordsBase() {
        this.base = new HashMap<>();
    }

    public Map<Word, Integer> getBase() {
        return this.base;
    }

    public void setBase(Map<Word, Integer> base) {
        if (base == null) {
            throw new NullPointerException("Base must not be null...");
        }
        this.base = base;
    }

    @Override
    public void printBase() {

    }
}

@Configuration
class TestConfiguration {

    @Bean
    public WordsBase<Integer> getWordsBase() {
        return new TestWordsBase();
    }
}
