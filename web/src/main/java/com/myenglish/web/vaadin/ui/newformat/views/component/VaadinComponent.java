package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.vaadin.flow.component.Component;

/**
 * Sets the interface for all other inseparable components.
 */
public interface VaadinComponent {

    /**
     * convert this component to {@link Component} of vaadin framework.
     *
     * @return prepared component.
     */
    Component operation();
}
