package com.myenglish.service;

import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.model.Word;
import com.myenglish.model.WordFactory;
import com.myenglish.service.config.ServiceConfig;
import com.myenglish.testdb.TestH2DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class, TestH2DB.class, DaoHibernateConfig.class})
@Transactional
public class WordServiceImplTest {

    @Autowired
    WordService wordService;

    @Test
    public void testSetupApp() {
        Assertions.assertNotNull(wordService);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void writeSavedWordToTheFileTest() {
        String result = "('english', 'russian', 'description', '2020-10-01'),";
        Word word = WordFactory.create().withEnglish("english")
                .withRussian("russian").withDescription("description")
                .withDateOfRegistry(LocalDate.of(2020, 10, 1))
                .build();
        File file = new File("src/test/resources/testSavedWords.txt");
        wordService.writeSavedWordToTheFile(word, file);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while(reader.ready()) {
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
