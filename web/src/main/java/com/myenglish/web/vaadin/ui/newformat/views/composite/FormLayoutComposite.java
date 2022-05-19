package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.ValidatePredicate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The composite contains vaadin components that is used for adding new words.
 */
public class FormLayoutComposite extends AbstractComposite implements ValidatePredicate {

    public FormLayoutComposite() {
        super(new HorizontalLayout());
    }

    @Override
    public HorizontalLayout getLayout() {
        return (HorizontalLayout) super.getLayout();
    }
}
