package com.myenglish.kafka.logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class TestConsumer {

    private ConsumerRecord<String, String> record;
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = {"logger"}, groupId = "1", containerFactory = "singleFactory")
    public void getMessage(ConsumerRecord<String, String> record) {
        System.out.println("record was delivered");
        this.record = record;
        latch.countDown();
    }

    public ConsumerRecord<String, String> getRecord() {
        return record;
    }

    public CountDownLatch getCountDownLatch() {
        return latch;
    }
}
