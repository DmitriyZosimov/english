package com.myenglish.dao;

import com.myenglish.model.Word;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Using for manipulating {@link Word} in database
 */
public interface WordDao {

    /**
     * Getting four random words.
     *
     * @return four random words from database
     */
    List<Word> getFourRandomWords();

    /**
     * Getting four random words.
     *
     * @return list of words which were saved after {@param date}
     */
    List<Word> getFourRandomWordsByDateFrom(LocalDate date);

    /**
     * Getting one random word.
     *
     * @return {@link Optional} with a {@link Word}.
     */
    Optional<Word> getRandomWord();

    /**
     * Getting one random word.
     *
     * @return {@link Optional} with a {@link Word} which was waved after {@param date}
     */
    Optional<Word> getRandomWordByDateFrom(LocalDate date);

    /**
     * Getting a word.
     *
     * @return {@link Optional} with {@link Word} by {@param english}
     */
    Optional<Word> getWordByEnglish(String english);

    /**
     * Saving or updating a word
     *
     * @return saved or updated {@link Word}
     */
    Word saveOrUpdateWord(Word word);
}
