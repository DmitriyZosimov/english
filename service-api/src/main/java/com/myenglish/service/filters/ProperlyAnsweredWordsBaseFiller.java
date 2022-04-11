package com.myenglish.service.filters;

import com.myenglish.model.Word;
import org.springframework.stereotype.Component;

@Component
public class ProperlyAnsweredWordsBaseFiller implements WordsBaseFiller {

    private WordsBase<Integer> base;

    public ProperlyAnsweredWordsBaseFiller(WordsBase<Integer> base) {
        this.base = base;
    }

    @Override
    public void fill(Word word) {
        int count = 0;
        if (base.getBase().containsKey(word)) {
            count = base.getBase().get(word);
        }
        count++;
        base.getBase().put(word, count);
    }
}
