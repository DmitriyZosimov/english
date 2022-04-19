package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.model.FourWordsDto;
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
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        return layout;
    }

    @Override
    public void handleRequest(Composite composite, FourWordsDto dto) {
        composite.add(this);
    }
}
