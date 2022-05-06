package com.myenglish.web.vaadin.ui.newformat.views.decorator;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.service.filters.WordsBaseFiller;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.myenglish.web.vaadin.ui.newformat.views.TranslateView;
import com.myenglish.web.vaadin.ui.newformat.views.component.DescriptionComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.InputComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.RussianWordComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.TitleComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonEnterComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonGetAnswerComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonNextComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.ButtonsComposite;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.composite.CompositeBuilder;
import com.myenglish.web.vaadin.ui.newformat.views.composite.InputComposite;
import com.myenglish.web.vaadin.ui.newformat.views.mediator.ResultMediator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Input mode decorator
 */
@PageTitle("Input")
@Route(value = "word/input", layout = MainLayout.class)
@VaadinSessionScope
public class InputDecorator extends VerticalLayout implements Decorator, Refreshable, CompositeBuilder {

    private TranslateView view;
    private WordService wordService;
    private WordsBaseFiller filler;

    private Component finalComponent;
    private Composite inputComposite;

    private ResultMediator resultMediator;

    @Autowired
    public InputDecorator(TranslateView view, WordService wordService, WordsBaseFiller filler) {
        removeAll();
        this.view = view;
        this.wordService = wordService;
        this.filler = filler;
        this.resultMediator = new ResultMediator();
        buildView();
    }

    @Override
    public Composite buildComposite() {
        this.inputComposite = buildInput();
        this.inputComposite.addFirst(buildTitle());
        return inputComposite;
    }

    @Override
    public void refresh() {
        clean();
        this.inputComposite = buildComposite();
        finalComponent = this.inputComposite.operation();
        addComponentAsFirst(finalComponent);
    }

    @Override
    public Component buildView() {
        this.finalComponent = buildComposite().operation();
        add(finalComponent, view.buildView());
        return finalComponent;
    }

    @Override
    public void clean() {
        remove(finalComponent);
        List<Component> comps = this.getChildren().filter(comp -> comp.getId().orElse("").equals("get-answer-layout")).collect(Collectors.toList());
        comps.forEach(this::remove);
    }

    private InputComposite buildInput() {
        InputComposite inputComposite = new InputComposite();
        Word word = getWord();

        ButtonsComposite buttonsComposite = new ButtonsComposite(ComponentId.BUTTONS_INPUT);
        buttonsComposite.getLayout().setJustifyContentMode(JustifyContentMode.CENTER);
        InputComponent inputComponent = new InputComponent(buttonsComposite);
        DescriptionComponent descriptionComponent = new DescriptionComponent(inputComponent);
        RussianWordComponent russianWordComponent = new RussianWordComponent(descriptionComponent);
        russianWordComponent.handleRequest(inputComposite, word);

        ButtonNextComponent nextButton = new ButtonNextComponent(this);
        ButtonGetAnswerComponent getAnswerButton = new ButtonGetAnswerComponent(this, nextButton);
        ButtonEnterComponent enterButton = new ButtonEnterComponent(filler, getAnswerButton, inputComponent.getTextFieldLayout());
        enterButton.handleRequest(buttonsComposite, word);

        resultMediator.setResult(new AtomicBoolean(false));
        enterButton.setResultMediator(this.resultMediator);
        inputComponent.setResultMediator(this.resultMediator);
        return inputComposite;
    }

    private Word getWord() {
        if (view.getDateFrom() != null) {
            return wordService.getRandomWordByDateFrom(view.getDateFrom()).orElse(null);
        } else {
            return wordService.getRandomWord().orElse(null);
        }
    }

    private TitleComponent buildTitle() {
        TitleComponent titleComponent = new TitleComponent();
        titleComponent.setTitle("Translation from russian to english: Input mode");
        return titleComponent;
    }
}
