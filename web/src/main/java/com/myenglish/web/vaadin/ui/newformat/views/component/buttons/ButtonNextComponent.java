package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.Decorator;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.Refreshable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

/**
 * Creates next button to get next words.
 */
public class ButtonNextComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private Refreshable refreshable;

    public ButtonNextComponent(Refreshable refreshable) {
        this.refreshable = refreshable;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.NEXT);
        button.addClickShortcut(Key.ARROW_RIGHT, KeyModifier.CONTROL);
        button.addClickListener(event -> refreshable.refresh());
        div.add(button);
        return div;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        composite.add(this);
    }
}
