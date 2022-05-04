package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
import com.myenglish.service.filters.WordsBaseFiller;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.TestDecorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Component creates four buttons with english words for test mode translation.
 */
public class TestButtonsComponent implements VaadinComponent, ChainOfResponsibilityHandler {

    private FourWordsDto dto;
    private WordsBaseFiller filler;
    private TestDecorator testDecorator;
    private ChainOfResponsibilityHandler nextHandler;

    public TestButtonsComponent(TestDecorator testDecorator, WordsBaseFiller filler,
                                ChainOfResponsibilityHandler handler) {
        this.filler = filler;
        this.testDecorator = testDecorator;
        this.nextHandler = handler;
    }

    @Override
    public Component operation() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        for (Word word : dto.getFourRandomWords()) {
            verticalLayout.add(buildButton(word));
        }
        return verticalLayout;
    }

    private Component buildButton(Word currentWord) {
        Label buttonLabel = new Label(currentWord.getEnglish());
        Button wordButton = new Button(buttonLabel);
        wordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        wordButton.isDisableOnClick();
        wordButton.setWidth(450f, Unit.PIXELS);
        wordButton.setHeight(50f, Unit.PIXELS);
        wordButton.addClickListener(event -> {
            if (dto.getCorrectWord() == currentWord) {
                wordButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                filler.fill(currentWord);
                testDecorator.refresh();
            } else {
                wordButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
                filler.remove(currentWord);
            }
            wordButton.removeThemeVariants(ButtonVariant.LUMO_CONTRAST);
        });
        return wordButton;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        if (word instanceof FourWordsDto) {
            this.dto = (FourWordsDto) word;
        } else {
            throw new IllegalArgumentException("Can't get Dto. The object " + word
                    + " must be a class instance of FourWordDto.class");
        }
        composite.add(this);
        nextHandler.handleRequest(composite, word);
    }
}
