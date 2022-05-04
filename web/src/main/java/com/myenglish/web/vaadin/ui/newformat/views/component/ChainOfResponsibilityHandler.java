package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;

/**
 * Organizes independent of object sender chain. Objects of this chain can fulfill a request.
 */
public interface ChainOfResponsibilityHandler {

    /**
     * handle current request and send to next handler.
     *
     * @param composite object keeps simple inseparable components.
     * @param word      contains object with word(s) that will be guessed.
     */
    void handleRequest(Composite composite, Object word);
}
