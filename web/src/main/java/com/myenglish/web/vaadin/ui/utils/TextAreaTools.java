package com.myenglish.web.vaadin.ui.utils;

import com.vaadin.flow.component.textfield.TextArea;

/**
 * Class is for building common text areas
 */
public class TextAreaTools {

    /**
     * Build text area for description text field in form layout
     * @return text area component
     */
    public static TextArea buildDescriptionArea() {
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPlaceholder("write a description (optional)");
        descriptionArea.setClearButtonVisible(true);
        return descriptionArea;
    }
}
