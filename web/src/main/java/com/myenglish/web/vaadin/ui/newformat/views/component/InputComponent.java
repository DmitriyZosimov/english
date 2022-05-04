package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.Word;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.mediator.ResultMediator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.concurrent.atomic.AtomicBoolean;

public class InputComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private AtomicBoolean result;
    private Word word;
    private ChainOfResponsibilityHandler nextHandler;
    private HorizontalLayout textFieldLayout;
    private ResultMediator resultMediator;

    public InputComponent(ChainOfResponsibilityHandler nextHandler) {
        this.nextHandler = nextHandler;
        this.result = new AtomicBoolean(false);
        this.textFieldLayout = new HorizontalLayout();
    }

    @Override
    public Component operation() {
        textFieldLayout.removeAll();
        TextField answerField = new TextField();
        answerField.setAutofocus(true);
        answerField.setWidth(500L, Unit.PIXELS);
        answerField.setClearButtonVisible(true);
        answerField.addValueChangeListener(event -> {
            result.set(answerField.getValue().equals(word.getEnglish()));
            if (resultMediator != null) {
                resultMediator.setResult(result);
            }
        });
        this.textFieldLayout.add(answerField);
        this.textFieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return this.textFieldLayout;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        if (word instanceof Word) {
            this.word = (Word) word;
            composite.add(this);
            nextHandler.handleRequest(composite, word);
        }
    }

    public HorizontalLayout getTextFieldLayout() {
        return textFieldLayout;
    }

    public void setResultMediator(ResultMediator resultMediator) {
        this.resultMediator = resultMediator;
    }
}
