package com.myenglish.web.vaadin.ui.newformat.views.composite;

/**
 * Build a composite that contains VaadinComponents to further build view from this composite.
 */
public interface CompositeBuilder {

    /**
     * Builds combination more simple objects
     *
     * @return object that keep simple inseparable components ({@link com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent})
     */
    Composite buildComposite();
}
