package com.myenglish.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordTest {

    @Test
    public void testToString() {
        Word word = WordBuilder.create().withId(1).withEnglish("hello").withRussian("здравствуйте").build();
        System.out.println(word);
        Assertions.assertNotNull(word);
    }
}
