package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The composite contains batch of buttons.
 */
public class ButtonsComposite extends AbstractComposite implements ChainOfResponsibilityHandler {

    public ButtonsComposite(String id) {
        super(new HorizontalLayout());
        ((HorizontalLayout) super.getLayout()).setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        setId(id);
    }

    @Override
    public void handleRequest(Composite composite, Object dto) {
        composite.add(this);
    }

    @Override
    public HorizontalLayout getLayout() {
        return (HorizontalLayout) super.getLayout();
    }
}
