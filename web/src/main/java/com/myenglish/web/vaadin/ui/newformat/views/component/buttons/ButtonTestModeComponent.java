package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.TestDecorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

/**
 * Creates button to switch to test mode that is used test decorator.
 */
public class ButtonTestModeComponent implements VaadinComponent {

    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.TEST_MODE);
        button.addClickListener(event -> UI.getCurrent().navigate(TestDecorator.class));
        div.add(button);
        return div;
    }
}
