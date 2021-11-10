package com.myenglish.service;

import com.myenglish.dao.WordDao;
import com.myenglish.model.Word;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WordServiceImpl implements WordService {

    private WordDao wordDao;
    private static final String filePath = System.getProperty("user.home") + "/.english/savedWords.txt";

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
        Word savedWord = wordDao.saveOrUpdateWord(word);
        writeSavedWordToTheFile(savedWord, null);
        return savedWord;
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void writeSavedWordToTheFile(Word word, @Nullable File file) {
        if(file == null) {
            file = new File(filePath);
        }
        String resultString = String.format("('%s', '%s', '%s', '%s'),%n",
                word.getEnglish(), word.getRussian(), word.getDescription(),
                word.getDateOfRegistry().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            printWriter.print(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
