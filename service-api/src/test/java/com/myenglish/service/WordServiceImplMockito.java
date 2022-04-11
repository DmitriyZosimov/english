package com.myenglish.service;

import com.myenglish.dao.WordDao;
import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
import com.myenglish.model.WordBuilder;
import com.myenglish.service.filters.DefaultRepeatFilter;
import com.myenglish.service.filters.RepeatFilter;
import com.myenglish.service.filters.WordsBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class WordServiceImplMockito extends AppenderMockitoExtension {

    @InjectMocks
    WordServiceImpl wordService;

    @Mock
    WordDao wordDao;
    @Mock
    LoggerProducer loggerProducer;
    @Mock
    WordsBase<Integer> base;
    @Spy
    RepeatFilter repeatFilter = new DefaultRepeatFilter(this.base);
    @Spy
    CounterOfAddressingToDB addressingToDB = new CounterOfAddressingToDB();

    List<Word> words;

    @BeforeEach
    public void setup() {
        buildWordsList();
        buildDefaultProperlyAnsweredWordBase();
        setupWordDao();
        setupSpies();
    }

    @Test
    public void getFourWordsDtoTest() {
        FourWordsDto dto = wordService.getFourRandomWords();
        Assertions.assertNotNull(dto);
        verify(wordDao, atMost(5)).getFourRandomWords();
    }

    @Test
    public void getFourWordsDtoWhenAllWordsAreAlreadyRepeatedMaxTimes() {
        base.getBase().put(words.get(3), 2);
        FourWordsDto dto = wordService.getFourRandomWords();
        Assertions.assertNotNull(dto);
        verify(wordDao, times(5)).getFourRandomWords();
    }

    @Test
    public void getFourRandomWordsByDateFromTest() {
        FourWordsDto dto = wordService.getFourRandomWordsByDateFrom(LocalDate.now());
        Assertions.assertNotNull(dto);
        verify(wordDao, atMost(5)).getFourRandomWordsByDateFrom(any());
    }

    @Test
    public void getFourRandomWordsByDateFromWhenAllWordsAreAlreadyRepeatedMaxTimes() {
        base.getBase().put(words.get(3), 2);
        FourWordsDto dto = wordService.getFourRandomWordsByDateFrom(LocalDate.now());
        Assertions.assertNotNull(dto);
        verify(wordDao, times(5)).getFourRandomWordsByDateFrom(any());
    }

    private void setupSpies() {
        buildRepeatFilter();
        buildCounterOfAddressingToDB();
    }

    private void buildDefaultProperlyAnsweredWordBase() {
        Map<Word, Integer> map = new HashMap<>();

        for (int i = 0; i < words.size() - 1; i++) {
            Word word = words.get(i);
            map.put(word, 2);
        }

        map.put(words.get(3), 1);
        when(base.getBase()).thenReturn(map);
    }

    private void buildRepeatFilter() {
        ((DefaultRepeatFilter) repeatFilter).setMax(2);
        ((DefaultRepeatFilter) repeatFilter).setBase(this.base);
    }

    private void buildCounterOfAddressingToDB() {
        addressingToDB.setMax(5);
    }

    private void setupWordDao() {
        when(wordDao.getFourRandomWords()).thenReturn(words);
        when(wordDao.getFourRandomWordsByDateFrom(any())).thenReturn(words);
    }

    private void buildWordsList() {
        this.words = new ArrayList<>();
        words.add(buildWord(1, "first", "russianFirst"));
        words.add(buildWord(2, "second", "russianSecond"));
        words.add(buildWord(3, "third", "russianThird"));
        words.add(buildWord(4, "fourth", "russianFourth"));
    }

    private Word buildWord(int id, String english, String russian) {
        return WordBuilder.create().withId(id).withEnglish(english)
                .withRussian(russian).withDateOfRegistry(LocalDate.now()).build();
    }
}
