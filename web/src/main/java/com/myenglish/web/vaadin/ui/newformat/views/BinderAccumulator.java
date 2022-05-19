package com.myenglish.web.vaadin.ui.newformat.views;

import com.vaadin.flow.data.binder.Binder;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Instance contains a collection of T components that will be used for saving them in DB.
 * @param <T> type of components that will be used by services for further saving in DB.
 */
public class BinderAccumulator<T> {

    private Queue<Binder<T>> accumulator;

    public BinderAccumulator() {
        this.accumulator = new LinkedList<>();
    }

    public Queue<Binder<T>> getAccumulator() {
        return accumulator;
    }
}
