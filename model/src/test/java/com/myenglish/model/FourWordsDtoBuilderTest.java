package com.myenglish.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FourWordsDtoBuilderTest {

    @Test
    public void creatingFourWordsTest() {
        List<Word> words = getWords();
        FourWordsDto dto = FourWordsDtoBuilder.create().withFourRandomWords(words).build();

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getCorrectWord());
        Assertions.assertEquals(words, dto.getFourRandomWords());
        Assertions.assertTrue(dto.getFourRandomWords().contains(dto.getCorrectWord()));

    }

    private List<Word> getWords() {
        Word word1 = WordBuilder.create().withId(1).withEnglish("first").build();
        Word word2 = WordBuilder.create().withId(2).withEnglish("second").build();
        Word word3 = WordBuilder.create().withId(3).withEnglish("third").build();
        Word word4 = WordBuilder.create().withId(4).withEnglish("fourth").build();

        List<Word> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);
        words.add(word4);
        return words;
    }
}
