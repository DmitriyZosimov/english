package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

/**
 * The composite contains components that are used in input mode of translation from russian.
 */
public class InputComposite extends AbstractComposite {

    public InputComposite() {
        setId(ComponentId.WORD_INPUT);
        ((FlexComponent) super.getLayout()).setAlignItems(FlexComponent.Alignment.CENTER);
    }
}
