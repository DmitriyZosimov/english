package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.vaadin.flow.component.HasComponents;

/**
 * The composite may be used like body of html.
 */
public class BodyComposite extends AbstractComposite {

    public BodyComposite() {
        setId(ComponentId.BODY);
    }

    public BodyComposite(HasComponents layout) {
        super(layout);
        setId(ComponentId.BODY);
    }
}
