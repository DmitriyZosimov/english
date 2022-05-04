package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.InputDecorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

/**
 * Creates input mode to switch current translation mode to input mode and further it will be generated input decorator.
 */
public class ButtonInputModeComponent implements VaadinComponent {
    //TODO: change navigation
    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.INPUT_MODE);
        button.addClickListener(event -> UI.getCurrent().navigate(InputDecorator.class));
        div.add(button);
        return div;
    }
}
