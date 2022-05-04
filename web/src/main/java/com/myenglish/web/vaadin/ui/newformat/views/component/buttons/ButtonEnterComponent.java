package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.model.Word;
import com.myenglish.service.filters.WordsBaseFiller;
import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.mediator.ResultMediator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Creates enter button that will request current answer to check if it's correct.
 */
public class ButtonEnterComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private WordsBaseFiller filler;
    private Word word;
    private ChainOfResponsibilityHandler nextHandler;
    private HasComponents layout;
    private ResultMediator resultMediator;

    public ButtonEnterComponent(WordsBaseFiller filler, ChainOfResponsibilityHandler nextHandler, HasComponents layout) {
        this.filler = filler;
        this.nextHandler = nextHandler;
        this.layout = layout;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button enterButton = new Button(ButtonsNames.ENTER);
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(event -> {
            Icon icon;
            if (resultMediator != null && resultMediator.getResult().get()) {
                icon = new Icon(VaadinIcon.CHECK);
                icon.setColor("green");
                filler.fill(word);
            } else {
                icon = new Icon(VaadinIcon.CLOSE_SMALL);
                icon.setColor("red");
                filler.remove(word);
            }
            layout.add(icon);
        });
        div.add(enterButton);
        return div;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        if (word instanceof Word) {
            this.word =(Word) word;
            composite.add(this);
            nextHandler.handleRequest(composite, word);
        }
    }

    public void setResultMediator(ResultMediator resultMediator) {
        this.resultMediator = resultMediator;
    }
}
