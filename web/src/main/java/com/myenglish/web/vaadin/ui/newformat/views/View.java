package com.myenglish.web.vaadin.ui.newformat.views;

import com.vaadin.flow.component.Component;

/**
 * Base interface of all views. And also it is a component - an element of a decorator design pattern.
 */
public interface View {

    /**
     * Create view page
     *
     * @return {@link Component} that is ready to add to vaadin UI
     */
    Component buildView();

}
