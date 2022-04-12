package com.myenglish.service.filters;

import com.myenglish.model.Word;

import java.util.Map;

/**
 * Interface of words base
 *
 * @param <T>
 */
public interface WordsBase<T> {

    /**
     * getting a words base where keys are {@code Word} and value are any objects.
     *
     * @return words base
     */
    Map<Word, T> getBase();

    /**
     * setting a new words base
     *
     * @param base - a new {@code Map} where keys are words and values are any objects.
     */
    void setBase(Map<Word, T> base);

    /**
     * It's used only for logging to check what words are already included.
     */
    void printBase();
}
