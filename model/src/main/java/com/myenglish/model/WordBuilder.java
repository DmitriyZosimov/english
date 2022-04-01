package com.myenglish.model;

import java.time.LocalDate;

public class WordBuilder {

    private static WordBuilder wordBuilder = new WordBuilder();
    private Integer id;
    private String english;
    private String russian;
    private String description;
    private LocalDate dateOfRegistry;
    private String transcription;

    public static WordBuilder create() {
        return wordBuilder;
    }

    public WordBuilder withId(Integer id) {
        this.id = id;
        return wordBuilder;
    }

    public WordBuilder withEnglish(String english) {
        this.english = english;
        return wordBuilder;
    }

    public WordBuilder withRussian(String russian) {
        this.russian = russian;
        return wordBuilder;
    }

    public WordBuilder withDescription(String description) {
        this.description = description;
        return wordBuilder;
    }

    public WordBuilder withDateOfRegistry(LocalDate localDate) {
        this.dateOfRegistry = localDate;
        return wordBuilder;
    }

    public WordBuilder withTranscription(String transcription) {
        this.transcription = transcription;
        return wordBuilder;
    }

    public Word build() {
        Word word = new Word();
        word.setId(id);
        word.setEnglish(english);
        word.setRussian(russian);
        word.setDescription(description);
        word.setDateOfRegistry(dateOfRegistry);
        word.setTranscription(transcription);
        return word;
    }
}
