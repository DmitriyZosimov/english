package com.myenglish.web.vaadin.ui.utils;

import com.vaadin.flow.component.textfield.TextField;

public class TextFieldTools {

    public static TextField buildTextField() {
        TextField textField = new TextField();
        textField.setPlaceholder("write a word");
        textField.setClearButtonVisible(true);
        textField.setWidth("400");
        return textField;
    }
}
