package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Abstract realization of all composite.
 */
public abstract class AbstractComposite implements Composite {

    private Deque<VaadinComponent> components;
    private HasComponents layout;
    private String id;

    public AbstractComposite() {
        this.components = new LinkedList<>();
        this.layout = new VerticalLayout();
    }

    public AbstractComposite(HasComponents layout) {
        this.layout = layout;
        this.components = new LinkedList<>();
    }

    public void setId(String id) {
        this.id = id;
        ((Component) layout).setId(this.id);
    }

    @Override
    public void add(VaadinComponent... components) {
        Arrays.stream(components).forEach(component -> this.components.offer(component));
    }

    @Override
    public void addFirst(VaadinComponent... components) {
        Arrays.stream(components).forEachOrdered(component -> this.components.offerFirst(component));
    }

    @Override
    public Component operation() {
        layout.removeAll();
        components.forEach(component -> layout.add(component.operation()));
        return (Component) layout;
    }

    public Deque<VaadinComponent> getComponents() {
        return components;
    }

    public HasComponents getLayout() {
        return layout;
    }
}
