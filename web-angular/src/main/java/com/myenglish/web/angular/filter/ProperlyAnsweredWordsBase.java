package com.myenglish.web.angular.filter;

import com.myenglish.model.Word;
import com.myenglish.service.filters.WordsBase;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains the database of correctly answered words that is used to decrease repeats.
 */
@Component
@SessionScope
public class ProperlyAnsweredWordsBase implements WordsBase<Integer> {

    private Map<Word, Integer> base;

    public ProperlyAnsweredWordsBase() {
        this.base = new HashMap<>();
    }

    public Map<Word, Integer> getBase() {
        return this.base;
    }

    public void setBase(Map<Word, Integer> base) {
        if (base == null) {
            throw new NullPointerException("Base must not be null...");
        }
        this.base = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProperlyAnsweredWordsBase that = (ProperlyAnsweredWordsBase) o;

        return base.equals(that.base);
    }

    @Override
    public int hashCode() {
        return 11 * base.hashCode();
    }
}
