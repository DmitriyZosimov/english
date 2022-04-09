package com.myenglish.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class doesn't allow infinite searching of words that won't be repeated. The searching stops when count of addressing
 * to db is more than max allowed times.
 */
@Component
public class CounterOfAddressingToDB {

    @Value("${counter.of.addressing.to.db.max}")
    private int max;
    private int count;

    public CounterOfAddressingToDB() {
        this.count = 0;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void increase() {
        this.count++;
    }

    public void reset() {
        this.count = 0;
    }

    public boolean noMoreThanMax() {
        return count < max;
    }
}
