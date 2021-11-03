package com.myenglish.dao;

import com.myenglish.model.Word;
import com.myenglish.model.WordFactory;
import com.myenglish.testdb.TestH2DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WordDaoHibernateConfigTest.class, TestH2DB.class})
@Transactional
public class WordDaoHibernateIT {

    @Autowired
    WordDao wordDao;

    @Test
    public void testApplicationConfiguration() {
        Assertions.assertNotNull(wordDao);
    }

    @Test
    public void getFourRandomWordsTest() {
        List<Word> words = wordDao.getFourRandomWords();
        Assertions.assertNotNull(words);
        Assertions.assertEquals(4, words.size());
    }

    @Test
    public void getRandomWordTest() {
        Optional<Word> opt = wordDao.getRandomWord();
        Assertions.assertTrue(opt.isPresent());
    }

    @Test
    public void getWordByEnglishTest() {
        String word = "hello";
        Optional<Word> opt = wordDao.getWordByEnglish(word);
        Assertions.assertTrue(opt.isPresent());
        Assertions.assertEquals(word, opt.get().getEnglish());
    }

    @Test
    public void getEmptyOptionalOfWordByEnglishTest() {
        String word = "yes";
        Optional<Word> opt = wordDao.getWordByEnglish(word);
        Assertions.assertFalse(opt.isPresent());
    }

    @Test
    public void saveWord() {
        Word word = WordFactory.create().withEnglish("market").withRussian("рынок").build();
        Word savedWord = wordDao.saveOrUpdateWord(word);
        Assertions.assertNotNull(savedWord.getId());
    }
}
