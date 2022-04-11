package com.myenglish.service;

import com.myenglish.model.Word;
import com.myenglish.model.WordBuilder;
import com.myenglish.service.filters.ProperlyAnsweredWordsBaseFiller;
import com.myenglish.service.filters.WordsBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ProperlyAnsweredWordsBaseFillerMockito extends AppenderMockitoExtension {

    @InjectMocks
    private ProperlyAnsweredWordsBaseFiller filler;

    @Mock
    private WordsBase<Integer> base;

    @BeforeEach
    public void setup() {
        Map<Word, Integer> map = new HashMap<>();
        when(base.getBase()).thenReturn(map);
    }

    @Test
    public void fillOneWordSeveralTimesTest() {
        Word word = buildWord(1, "english", "russian");

        filler.fill(word);
        Assertions.assertEquals(1, base.getBase().get(word));

        filler.fill(word);
        Assertions.assertEquals(2, base.getBase().get(word));

        filler.fill(word);
        Assertions.assertEquals(3, base.getBase().get(word));

        filler.fill(word);
        Assertions.assertEquals(4, base.getBase().get(word));
    }

    @Test
    public void removeWordFromBaseTest() {
        Word word = buildWord(1, "english", "russian");
        filler.fill(word);
        Assertions.assertEquals(1, base.getBase().get(word));

        filler.remove(word);
        Assertions.assertFalse(base.getBase().containsKey(word));

        filler.remove(word);
        Assertions.assertFalse(base.getBase().containsKey(word));

    }

    private Word buildWord(int id, String english, String russian) {
        return WordBuilder.create().withId(id).withEnglish(english)
                .withRussian(russian).withDateOfRegistry(LocalDate.now()).build();
    }
}
