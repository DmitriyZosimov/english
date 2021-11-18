package com.myenglish.web.vaadin.ui.utils;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

/**
 * Class for generating common labels
 */
public class LabelTools {

    /**
     * create a div component with a summary label for page
     * @param label a summary label
     * @return div component with a label.
     */
    public static Div createMainLabel(String label) {
        Div div = new Div();
        H2 text = new H2(label);
        div.add(text);
        return div;
    }
}
