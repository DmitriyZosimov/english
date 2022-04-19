package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.FourWordsDto;
import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;

/**
 * Component creates html div element with description of word that will be guessed.
 */
public class DescriptionComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private String description;
    private String id;
    private ChainOfResponsibilityHandler nextHandler;

    public DescriptionComponent(ChainOfResponsibilityHandler handler) {
        this.id = ComponentId.DESCRIPTION;
        this.nextHandler = handler;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        div.setId(this.id);
        Text text = new Text(this.description);
        div.add(text);
        div.setWidthFull();
        div.getElement().getStyle().set("text-align", "center");
        return div;
    }

    @Override
    public void handleRequest(Composite composite, FourWordsDto dto) {
        this.description = dto.getCorrectWord().getDescriptionOrBlank();
        composite.add(this);
        nextHandler.handleRequest(composite, dto);
    }
}
