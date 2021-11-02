package com.myenglish.service;

import com.myenglish.dao.WordDao;
import com.myenglish.model.Word;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WordServiceImpl implements WordService {

    private WordDao wordDao;

    public WordServiceImpl(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Word> getFourRandomWords() {
        return wordDao.getFourRandomWords();
    }

    @Override
    public List<Word> getFourRandomWordsByDateFrom(LocalDate date) {
        return wordDao.getFourRandomWordsByDateFrom(date);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Word> getRandomWord() {
        return wordDao.getRandomWord();
    }

    @Override
    public Optional<Word> getRandomWordByDateFrom(LocalDate date) {
        return wordDao.getRandomWordByDateFrom(date);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Word> getWordByEnglish(String english) {
        return wordDao.getWordByEnglish(english);
    }

    @Override
    public Word saveOrUpdateWord(Word word) {
        return wordDao.saveOrUpdateWord(word);
    }
}
