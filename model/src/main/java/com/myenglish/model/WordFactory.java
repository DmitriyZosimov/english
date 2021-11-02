package com.myenglish.model;

import java.time.LocalDate;

public class WordFactory {

    private static WordFactory wordFactory = new WordFactory();
    private Integer id;
    private String english;
    private String russian;
    private String description;
    private LocalDate dateOfRegistry;

    public static WordFactory create() {
        return wordFactory;
    }

    public WordFactory withId(Integer id) {
        this.id = id;
        return wordFactory;
    }

    public WordFactory withEnglish(String english) {
        this.english = english;
        return wordFactory;
    }

    public WordFactory withRussian(String russian) {
        this.russian = russian;
        return wordFactory;
    }

    public WordFactory withDescription(String description) {
        this.description = description;
        return wordFactory;
    }

    public WordFactory withDateOfRegistry(LocalDate localDate) {
        this.dateOfRegistry = localDate;
        return wordFactory;
    }

    public Word build() {
        Word word = new Word();
        word.setId(id);
        word.setEnglish(english);
        word.setRussian(russian);
        word.setDescription(description);
        word.setDateOfRegistry(dateOfRegistry);
        return word;
    }
}
