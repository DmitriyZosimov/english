package com.myenglish.model;

import java.util.List;

/**
 * Class contains four random words and one of them is transparently allotted as will be guessed.
 * This variant of transferring information needs for a filter.
 */
public class FourWordsDto {

    private Word correctWord;
    private List<Word> fourRandomWords;

    FourWordsDto(List<Word> fourRandomWords) {
        this.fourRandomWords = fourRandomWords;
        setCorrectWord();
    }

    private void setCorrectWord() {
        this.correctWord = fourRandomWords.get((int) (Math.random() * fourRandomWords.size()));
    }

    public Word getCorrectWord() {
        return this.correctWord;
    }

    public List<Word> getFourRandomWords() {
        return this.fourRandomWords;
    }
}
