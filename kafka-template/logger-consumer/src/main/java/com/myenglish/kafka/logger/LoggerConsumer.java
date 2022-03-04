package com.myenglish.kafka.logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * A custom logger which use Apache Kafka for getting a log message
 */
@Component
@Profile("kafka")
public class LoggerConsumer {

    private Queue<ConsumerRecord<String, String>> records;
    private CountDownLatch countDownLatch = null;

    public LoggerConsumer() {
        this.records = new LinkedList<>();
    }

    /**
     * The consumer reads message batch from {@link KafkaTopics}#LOGGER topic and only from 0 and 1 partitions and then
     * prints their.
     *
     * @param records message batch
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = KafkaTopics.LOGGER, partitions = {"0", "1"}),
            groupId = "logger", containerFactory = "batchFactory")
    public void getMessageFromFirstClient(List<ConsumerRecord<String, String>> records) {
        this.records.addAll(records);
        print();
    }

    /**
     * The consumer reads single message from {@link KafkaTopics}#LOGGER topic and only from 2 partition and then prints
     * it.
     *
     * @param record message
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = KafkaTopics.LOGGER, partitions = {"2"}),
            groupId = "logger", containerFactory = "singleFactory")
    public void getMessageFromSecondClient(ConsumerRecord<String, String> record) {
        this.records.add(record);
        print();
    }

    /**
     * Set {@link CountDownLatch} which is using in tests now.
     *
     * @param countDownLatch using for testing.
     */
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private void print() {
        while (!this.records.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            ConsumerRecord<String, String> record = records.poll();
            System.out.println(String.format("%s %s [%s] %s",
                    dateFormat.format(Calendar.getInstance().getTime()),
                    getLoggerLevel(record),
                    getClassName(record),
                    getValue(record)));
            countDown();
        }
    }

    private String getClassName(ConsumerRecord<String, String> record) {
        return new String(record.headers().lastHeader("Class").value());
    }

    private String getLoggerLevel(ConsumerRecord<String, String> record) {
        byte[] level = record.headers().lastHeader("LoggerLevel").value();
        if (level != null && level.length > 0) {
            return new String(level);
        } else {
            return "INFO";
        }
    }

    private String getValue(ConsumerRecord<String, String> record) {
        byte[] message = record.value().getBytes();
        if (message.length > 0) {
            return new String(message);
        } else {
            return "";
        }
    }

    private void countDown() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}