package com.myenglish.service.filters;

import com.myenglish.model.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Default realize of {@link RepeatFilter}
 * the max field is how many times can be repeated a word.
 */
@Component
public class DefaultRepeatFilter implements RepeatFilter {

    @Value("${filter.repeat.max}")
    private int max;
    private WordsBase<Integer> base;

    public DefaultRepeatFilter(WordsBase<Integer> base) {
        this.base = base;
    }

    @Override
    public boolean isRepeat(Word word) {
        return getRepeatCount(word) >= max;
    }

    public void setBase(WordsBase<Integer> base) {
        this.base = base;
    }

    private Integer getRepeatCount(Word word) {
        return base.getBase().getOrDefault(word, 0);
    }

    public void setMax(int max) {
        this.max = max;
    }
}
