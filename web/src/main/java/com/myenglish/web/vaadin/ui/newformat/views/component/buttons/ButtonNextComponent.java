package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.model.FourWordsDto;
import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.Decorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

/**
 * Creates next button to get next words.
 */
public class ButtonNextComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private Decorator decorator;

    public ButtonNextComponent(Decorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.NEXT);
        button.addClickShortcut(Key.ARROW_RIGHT, KeyModifier.CONTROL);
        button.addClickListener(event -> decorator.refresh());
        div.add(button);
        return div;
    }

    @Override
    public void handleRequest(Composite composite, FourWordsDto dto) {
        composite.add(this);
    }
}
