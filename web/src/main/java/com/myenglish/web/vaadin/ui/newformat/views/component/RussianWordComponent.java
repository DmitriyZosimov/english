package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.FourWordsDto;
import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

/**
 * Component creates html div element with russian word for translation.
 */
public class RussianWordComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private String russian;
    private String id;
    private ChainOfResponsibilityHandler nextHandler;

    public RussianWordComponent(ChainOfResponsibilityHandler handler) {
        this.id = ComponentId.RUSSIAN;
        this.nextHandler = handler;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        div.setId(id);
        H3 russian = new H3(this.russian);
        div.add(russian);
        div.setWidthFull();
        div.getElement().getStyle().set("text-align", "center");
        return div;
    }

    @Override
    public void handleRequest(Composite composite, FourWordsDto dto) {
        this.russian = dto.getCorrectWord().getRussian();
        composite.add(this);
        nextHandler.handleRequest(composite, dto);
    }
}
