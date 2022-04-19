package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;

/**
 * Keeps simple inseparable components that implements {@link VaadinComponent}
 */
public interface Composite extends VaadinComponent {

    /**
     * Adds components to the store of simple inseparable components.
     *
     * @param components array that will be added.
     */
    void add(VaadinComponent... components);

    /**
     * Adds components as first elements to the store.
     *
     * @param components array that will be added.
     */
    void addFirst(VaadinComponent... components);

}
