package com.myenglish.service.filters;

import com.myenglish.model.Word;

/**
 * Filter checks the word is included in a database of properly answered words (see {@link ProperlyAnsweredWordsBase}
 * and showed several times.
 * It means that the word is already answered (translated or other) correctly several times.
 * This interface help to decrease words repeats and to embrace more words for learning.
 */
public interface RepeatFilter {

    /**
     * Check the word is included in database of properly answered words (see {@link ProperlyAnsweredWordsBase} and
     * showed several times.
     *
     * @param word is that will be checked.
     * @return true if {@param word} is included and showed several times, otherwise false.
     */
    boolean isRepeat(Word word);
}
