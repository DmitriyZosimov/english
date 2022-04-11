package com.myenglish.service.filters;

import com.myenglish.model.Word;

/**
 * The instrument is to fill the current words base by properly answered words to avoid repeat words further.
 */
public interface WordsBaseFiller {

    /**
     * Fill a base by words.
     *
     * @param word - properly answered word.
     */
    void fill(Word word);

    /**
     * Remove the word from a base
     * @param word - will be removed
     */
    void remove(Word word);
}
