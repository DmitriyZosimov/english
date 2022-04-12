package com.myenglish.web.config;

import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.Word;
import com.myenglish.model.WordBuilder;
import com.myenglish.web.filter.ProperlyAnsweredWordsBase;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;

@ExtendWith(EasyMockExtension.class)
public class ProperlyAnsweredWordsBaseEasyMock {

    @Mock
    private LoggerProducer loggerProducer;

    @TestSubject
    private ProperlyAnsweredWordsBase base = new ProperlyAnsweredWordsBase(loggerProducer);

    @BeforeEach
    public void setup() {
        loggerProducer.debug(anyString(), anyObject());
        expectLastCall().andAnswer(() -> {
            String message = EasyMock.getCurrentArgument(0);
            System.out.println(message);
            Assertions.assertNotNull(message);
            return null;
        });
        replay(loggerProducer);

        base.setBase(buildBase());
    }

    @Test
    public void printBaseTest() {
        base.printBase();
    }

    private Map<Word, Integer> buildBase() {
        Word word1 = WordBuilder.create().withEnglish("english").withId(1).withRussian("russian").withDescription("description").build();
        Word word2 = WordBuilder.create().withEnglish("hello").withId(2).withRussian("world").withTranscription("[hello]").build();
        Word word3 = WordBuilder.create().withEnglish("nice").withId(3).withRussian("really").build();
        Word word4 = WordBuilder.create().withEnglish("fine").withId(4).withRussian("java").build();

        Map<Word, Integer> map = new HashMap<>();
        map.put(word1, 1);
        map.put(word2, 2);
        map.put(word3, 3);
        map.put(word4, 1);
        return map;
    }
}
