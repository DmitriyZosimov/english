package com.myenglish.web.filter;

import com.myenglish.kafka.logger.LoggerProducer;
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

    private LoggerProducer logger;
    private Map<Word, Integer> base;

    public ProperlyAnsweredWordsBase(LoggerProducer logger) {
        this.base = new HashMap<>();
        this.logger = logger;
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
    public void printBase() {
        StringBuilder message = new StringBuilder("\nProperly answered words base:\n");
        message.append(prepareHeader());
        message.append(getSeparateLine() + "\n");

        for (Map.Entry<Word, Integer> element : base.entrySet()) {
            message.append(prepareRow(element));
        }
        logger.debug(message.toString(), ProperlyAnsweredWordsBase.class);
    }

    private String prepareHeader() {
        return String.format("%-25s%s %-15s%s %-25s%s %-30s%s %s%n",
                "English", "|", "Transcription", "|", "Russian", "|",
                "Description", "|", "amount");
    }

    private String getSeparateLine() {
        String separateLine = new String("=====");
        return separateLine.repeat(22);
    }

    private String prepareRow(Map.Entry<Word, Integer> element) {
        Word word = element.getKey();
        return String.format("%-25s%s %-15s%s %-25s%s %-30s%s %d%n",
                word.getEnglish(), "|", word.getTranscriptionOrBlank(), "|", word.getRussian(), "|",
                word.getDescriptionOrBlank(), "|", element.getValue());
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
