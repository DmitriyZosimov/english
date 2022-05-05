package com.myenglish.web.vaadin.ui.newformat.views.decorator;

import com.myenglish.web.vaadin.ui.newformat.views.View;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;

/**
 * Aggregates the decorated {@link View} as a component type and inherits his implementation.
 * Defines an interface for decorator's subclasses.
 */
public interface Decorator extends View {

    /**
     * Builds combination more simple objects
     *
     * @return object that keep simple inseparable components ({@link com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent})
     */
    Composite buildComposite();
}
