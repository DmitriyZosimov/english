package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.model.Word;
import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.ChainOfResponsibilityHandler;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasOrderedComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ButtonGetAnswerComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private Word word;
    private HasOrderedComponents component;
    private ChainOfResponsibilityHandler nextHandler;

    public ButtonGetAnswerComponent(HasOrderedComponents component, ChainOfResponsibilityHandler nextHandler) {
        this.component = component;
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        if (word instanceof Word) {
            this.word = (Word) word;
            composite.add(this);
            nextHandler.handleRequest(composite, word);
        }
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button answerButton = new Button(ButtonsNames.GET_ANSWER);
        answerButton.addClickListener(event -> {
            VerticalLayout answerLayout = new VerticalLayout(
                    new Text(word.getEnglish()),
                    prepareTranscription(word.getTranscription()));
            answerLayout.setId("get-answer-layout");
            answerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            answerLayout.setMargin(false);
            this.component.addComponentAtIndex(this.component.getComponentCount() - 1, answerLayout);
        });
        div.add(answerButton);
        return div;
    }

    private Div prepareTranscription(String transcription) {
        Text text;
        if (transcription != null) {
            text = new Text(transcription);
        } else {
            text = new Text("no transcription");
        }
        Div transcriptionDiv = new Div(text);
        transcriptionDiv.getStyle().set("color", "grey");
        return transcriptionDiv;
    }

}
