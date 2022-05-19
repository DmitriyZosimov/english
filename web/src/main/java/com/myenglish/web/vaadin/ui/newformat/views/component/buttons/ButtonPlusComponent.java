package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.Decorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

/**
 * Contains vaadin component {@link Button}. The button is used for adding extra layouts to already exists.
 * For example, it used for adding FormLayoutComponent.
 */
public class ButtonPlusComponent implements VaadinComponent {

    private Decorator decorator;

    public ButtonPlusComponent(Decorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.PLUS);
        button.addClickListener(event -> {
            decorator.buildView();
        });
        div.add(button);
        return div;
    }
}
