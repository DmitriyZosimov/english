package com.myenglish.model;

import java.util.List;

public class FourWordsDtoBuilder {

    private static FourWordsDtoBuilder builder = new FourWordsDtoBuilder();
    private List<Word> fourRandomWords;

    private FourWordsDtoBuilder() {
    }

    public static FourWordsDtoBuilder create() {
        return builder;
    }

    public FourWordsDtoBuilder withFourRandomWords(List<Word> fourRandomWords) {
        this.fourRandomWords = fourRandomWords;
        return builder;
    }

    public FourWordsDto build() {
        return new FourWordsDto(fourRandomWords);
    }

}
