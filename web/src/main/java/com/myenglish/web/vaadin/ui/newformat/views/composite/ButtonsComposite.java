package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The composite contains batch of buttons.
 */
public class ButtonsComposite extends AbstractComposite implements ChainOfResponsibilityHandler {

    private HorizontalLayout layout;

    public ButtonsComposite(String id) {
        setId(id);
        this.layout = new HorizontalLayout();
    }

    @Override
    public Component operation() {
        layout.removeAll();
        super.getComponents().forEach(component -> layout.add(component.operation()));
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return layout;
    }

    @Override
    public void handleRequest(Composite composite, Object dto) {
        composite.add(this);
    }

    @Override
    public HorizontalLayout getLayout() {
        return this.layout;
    }
}
