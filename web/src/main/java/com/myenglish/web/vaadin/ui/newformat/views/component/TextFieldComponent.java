package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.web.vaadin.ui.newformat.views.PlaceholderNames;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Text field is used to enter word, phrase, symbols.
 */
public class TextFieldComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private ChainOfResponsibilityHandler nextHandler;
    private String placeholder = PlaceholderNames.ENTER_WORD;

    public TextFieldComponent() {

    }

    public TextFieldComponent(ChainOfResponsibilityHandler nextHandler) {
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
        TextField textField = new TextField();
        textField.setPlaceholder(placeholder);
        textField.setClearButtonVisible(true);
        textField.setWidth("600");
        return textField;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
