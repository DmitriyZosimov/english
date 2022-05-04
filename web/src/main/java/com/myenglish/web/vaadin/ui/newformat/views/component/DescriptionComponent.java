package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
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
    public void handleRequest(Composite composite, Object word) {
        if (word instanceof FourWordsDto) {
            this.description = ((FourWordsDto) word).getCorrectWord().getDescriptionOrBlank();
        } else if (word instanceof Word) {
            this.description = ((Word) word).getDescriptionOrBlank();
        } else {
            throw new IllegalArgumentException("Can't get Description of the word. The object " + word
                    + " must be a class instance of FourWordDto.class or Word.class");
        }
        composite.add(this);
        nextHandler.handleRequest(composite, word);
    }
}
