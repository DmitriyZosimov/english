package com.myenglish.web.vaadin.ui.newformat.views.decorator;

/**
 * Refresh content in vaadin component
 */
public interface Refreshable {

    /**
     * Refresh current component.
     */
    void refresh();

    /**
     * Clean vaadin components from parent component for further refreshing.
     */
    void clean();
}
