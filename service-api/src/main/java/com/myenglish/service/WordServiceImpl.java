package com.myenglish.service;

import com.myenglish.dao.WordDao;
import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.FourWordsDto;
import com.myenglish.model.FourWordsDtoBuilder;
import com.myenglish.model.Word;
import com.myenglish.service.filters.RepeatFilter;
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
    private RepeatFilter repeatFilter;
    private CounterOfAddressingToDB addressingToDB;
    private LoggerProducer logger;
    private static final String filePath = System.getProperty("user.home") + "/.english/savedWords.txt";

    public WordServiceImpl(WordDao wordDao, RepeatFilter repeatFilter, CounterOfAddressingToDB addressingToDB, LoggerProducer loggerProducer) {
        this.wordDao = wordDao;
        this.repeatFilter = repeatFilter;
        this.addressingToDB = addressingToDB;
        this.logger = loggerProducer;
    }

    @Transactional(readOnly = true)
    @Override
    public FourWordsDto getFourRandomWords() {
        logger.debug("using getFourRandomWords...", WordServiceImpl.class);
        FourWordsDto fourWordsDto = saveWordsToFourWordsDto(wordDao.getFourRandomWords());
        addressingToDB.increase();
        if (addressingToDB.noMoreThanMax() && repeatFilter.isRepeat(fourWordsDto.getCorrectWord())) {
            return getFourRandomWords();
        } else {
            addressingToDB.reset();
        }
        return fourWordsDto;
    }

    @Override
    public FourWordsDto getFourRandomWordsByDateFrom(LocalDate date) {
        logger.debug("using getFourRandomWordsByDateFrom with date:" + date, WordServiceImpl.class);
        FourWordsDto fourWordsDto = saveWordsToFourWordsDto(wordDao.getFourRandomWordsByDateFrom(date));
        addressingToDB.increase();
        if (addressingToDB.noMoreThanMax() && repeatFilter.isRepeat(fourWordsDto.getCorrectWord())) {
            return getFourRandomWordsByDateFrom(date);
        } else {
            addressingToDB.reset();
        }
        return fourWordsDto;
    }

    private FourWordsDto saveWordsToFourWordsDto(List<Word> words) {
        logger.debug("saving words to FourWordsDto in saveWordsToFourDto()", WordServiceImpl.class);
        return FourWordsDtoBuilder.create().withFourRandomWords(words).build();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Word> getRandomWord() {
        logger.debug("using getRandomWord...", WordServiceImpl.class);
        return wordDao.getRandomWord();
    }

    @Override
    public Optional<Word> getRandomWordByDateFrom(LocalDate date) {
        logger.debug("using getRandomWordByDateFrom with date:" + date, WordServiceImpl.class);
        return wordDao.getRandomWordByDateFrom(date);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Word> getWordByEnglish(String english) {
        logger.debug("using getWordByEnglish with english:" + english, WordServiceImpl.class);
        return wordDao.getWordByEnglish(english);
    }

    @Override
    public Word saveOrUpdateWord(Word word) {
        logger.debug("using saveOrUpdateWord with word:" + word, WordServiceImpl.class);
        Word savedWord = wordDao.saveOrUpdateWord(word);
        writeSavedWordToTheFile(savedWord, null);
        return savedWord;
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void writeSavedWordToTheFile(Word word, @Nullable File file) {
        logger.debug("using writeSavedWordToTheFile with:" + word + " and file:" + file, WordServiceImpl.class);
        if (file == null) {
            file = new File(filePath);
        }
        String resultString = String.format("('%s', '%s', '%s', '%s'),%n",
                word.getEnglish(), word.getRussian(), word.getDescription(),
                word.getDateOfRegistry().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            printWriter.print(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
