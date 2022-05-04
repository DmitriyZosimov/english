package com.myenglish.web.vaadin.ui.newformat.views.mediator;

import java.util.concurrent.atomic.AtomicBoolean;

public class ResultMediator {

    private AtomicBoolean result;

    public AtomicBoolean getResult() {
        return result;
    }

    public void setResult(AtomicBoolean result) {
        this.result = result;
    }
}
