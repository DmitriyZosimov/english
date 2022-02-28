package com.myenglish.kafka.logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoggerProucerImplTest {

    @InjectMocks
    private LoggerProducerImpl producer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ListenableFuture<SendResult<String, String>> result;
    @Mock
    private SendResult<String, String> sendResult;
    @Captor
    private ArgumentCaptor<ProducerRecord<String, String>> argumentCaptor;

    private static final String MESSAGE = "MESSAGE";
    private static final String KEY = "KEY";
    private static final String TOPIC = KafkaTopics.LOGGER;

    @BeforeEach
    public void setup() {
        when(kafkaTemplate.send(argumentCaptor.capture())).thenReturn(result);
        doNothing().when(kafkaTemplate).flush();
        doAnswer(invocation -> {
            SuccessCallback<SendResult<String, String>> arg0 = invocation.getArgument(0);
            arg0.onSuccess(sendResult);
            // uncommit if you want get fail results
//            FailureCallback arg1 = invocation.getArgument(1);
//            arg1.onFailure(new Throwable("Error message"));
            return null;
        }).when(result).addCallback(any(), any());
    }

    @AfterEach
    public void commonVerifies() {
        verify(result, times(1)).addCallback(any(), any());
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
        verify(kafkaTemplate, times(1)).flush();

        assertEquals(MESSAGE, argumentCaptor.getValue().value());
        assertEquals(TOPIC, argumentCaptor.getValue().topic());
    }

    @Test
    public void sendLog_WithOneParam() {
        producer.sendLog(MESSAGE);
    }

    @Test
    public void sendLog_WithTwoParams() {
        producer.sendLog(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void sendLog_WithThreeParams() {
        producer.sendLog(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void trace_WithOneParam() {
        producer.trace(MESSAGE);
    }

    @Test
    public void trace_WithTwoParams() {
        producer.trace(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void trace_WithThreeParams_WhenHeadersExist() {
        producer.trace(MESSAGE, KEY, buildHeaders(LoggerLevel.TRACE));

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void trace_WithThreeParams_WhenHeadersNotExist() {
        producer.trace(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.TRACE.name(), logLevel);
    }

    @Test
    public void trace_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.trace(MESSAGE, KEY, new ArrayList<>());

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.TRACE.name(), logLevel);
    }

    @Test
    public void debug_WithOneParam() {
        producer.debug(MESSAGE);
    }

    @Test
    public void debug_WithTwoParams() {
        producer.debug(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void debug_WithThreeParams_WhenHeadersExist() {
        producer.debug(MESSAGE, KEY, buildHeaders(LoggerLevel.DEBUG));

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void debug_WithThreeParams_WhenHeadersNotExist() {
        producer.debug(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.DEBUG.name(), logLevel);
    }

    @Test
    public void debug_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.debug(MESSAGE, KEY, new ArrayList<>());

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.DEBUG.name(), logLevel);
    }

    @Test
    public void info_WithOneParam() {
        producer.info(MESSAGE);
    }

    @Test
    public void info_WithTwoParams() {
        producer.info(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void info_WithThreeParams_WhenHeadersExist() {
        producer.info(MESSAGE, KEY, buildHeaders(LoggerLevel.INFO));

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void info_WithThreeParams_WhenHeadersNotExist() {
        producer.info(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.INFO.name(), logLevel);
    }

    @Test
    public void info_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.info(MESSAGE, KEY, new ArrayList<>());

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.INFO.name(), logLevel);
    }

    @Test
    public void warn_WithOneParam() {
        producer.warn(MESSAGE);
    }

    @Test
    public void warn_WithTwoParams() {
        producer.warn(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void warn_WithThreeParams_WhenHeadersExist() {
        producer.warn(MESSAGE, KEY, buildHeaders(LoggerLevel.WARN));

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void warn_WithThreeParams_WhenHeadersNotExist() {
        producer.warn(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.WARN.name(), logLevel);
    }

    @Test
    public void warn_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.warn(MESSAGE, KEY, new ArrayList<>());

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.WARN.name(), logLevel);
    }

    @Test
    public void error_WithOneParam() {
        producer.error(MESSAGE);
    }

    @Test
    public void error_WithTwoParams() {
        producer.error(MESSAGE, KEY);

        assertEquals(KEY, argumentCaptor.getValue().key());
    }

    @Test
    public void error_WithThreeParams_WhenHeadersExist() {
        producer.error(MESSAGE, KEY, buildHeaders(LoggerLevel.ERROR));

        assertEquals(KEY, argumentCaptor.getValue().key());
        assertNotNull(argumentCaptor.getValue().headers());
    }

    @Test
    public void error_WithThreeParams_WhenHeadersNotExist() {
        producer.error(MESSAGE, KEY, null);

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.ERROR.name(), logLevel);
    }

    @Test
    public void error_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.error(MESSAGE, KEY, new ArrayList<>());

        assertEquals(KEY, argumentCaptor.getValue().key());
        String logLevel = new String(argumentCaptor.getValue().headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.ERROR.name(), logLevel);
    }

    private Collection<Header> buildHeaders(LoggerLevel loggerLevel) {
        Collection<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("LoggerLevel", loggerLevel.name().getBytes()));
        return headers;
    }
}
