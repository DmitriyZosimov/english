package com.myenglish.service;

import com.myenglish.model.Word;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WordService {

    List<Word> getFourRandomWords();
    List<Word> getFourRandomWordsByDateFrom(LocalDate date);
    Optional<Word> getRandomWord();
    Optional<Word> getRandomWordByDateFrom(LocalDate date);
    Optional<Word> getWordByEnglish(String english);
    Word saveOrUpdateWord(Word word);
}
