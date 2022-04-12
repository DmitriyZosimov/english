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

    @Test
    public void getTranscriptionOrBlanketTestIfTranscriptionIsNotNull() {
        Word word = WordBuilder.create().withId(1).withEnglish("hello").withRussian("здравствуйте")
                .withTranscription("[transcription]").build();
        String transcription = word.getTranscriptionOrBlank();
        Assertions.assertEquals("[transcription]", transcription);
    }

    @Test
    public void getTranscriptionOrBlanketTestIfTranscriptionIsNull() {
        Word word = WordBuilder.create().withId(1).withEnglish("hello").withRussian("здравствуйте").build();
        String transcription = word.getTranscriptionOrBlank();
        Assertions.assertEquals("", transcription);
    }

    @Test
    public void getDescriptionOrBlanketTestIfDescriptionIsNotNull() {
        Word word = WordBuilder.create().withId(1).withEnglish("hello").withRussian("здравствуйте")
                .withDescription("description").build();
        String description = word.getDescriptionOrBlank();
        Assertions.assertEquals("description", description);
    }

    @Test
    public void getDescriptionOrBlanketTestIfDescriptionIsNull() {
        Word word = WordBuilder.create().withId(1).withEnglish("hello").withRussian("здравствуйте").build();
        String description = word.getDescriptionOrBlank();
        Assertions.assertEquals("", description);
    }
}
