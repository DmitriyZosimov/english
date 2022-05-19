package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;

/**
 * Text area is used to enter a large text. The text area now is used for description field.
 */
public class TextAreaComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private ChainOfResponsibilityHandler nextHandler;
    private String placeholder;

    public TextAreaComponent() {
    }

    public TextAreaComponent(ChainOfResponsibilityHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        if (nextHandler != null) {
            composite.add(this);
            nextHandler.handleRequest(composite, word);
        }
    }

    @Override
    public Component operation() {
        TextArea descriptionArea = new TextArea();
        descriptionArea.setMinWidth("600");
        if (this.placeholder != null) {
            descriptionArea.setPlaceholder(placeholder);
        }
        descriptionArea.setClearButtonVisible(true);
        return descriptionArea;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
