package com.myenglish.web.vaadin.ui.newformat.views.decorator;

import com.myenglish.model.FourWordsDto;
import com.myenglish.service.WordService;
import com.myenglish.service.filters.WordsBaseFiller;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.myenglish.web.vaadin.ui.newformat.views.TranslateView;
import com.myenglish.web.vaadin.ui.newformat.views.component.DescriptionComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.RussianWordComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.TestButtonsComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.TitleComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonNextComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.ButtonsComposite;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.myenglish.web.vaadin.ui.newformat.views.composite.TestComposite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test mode decorator
 */
@PageTitle("Test")
@Route(value = "word/test", layout = MainLayout.class)
@VaadinSessionScope
public class TestDecorator extends VerticalLayout implements Decorator {

    private TranslateView view;
    private WordService wordService;
    private WordsBaseFiller filler;

    private Component finalComponent;
    private Composite testComposite;

    @Autowired
    public TestDecorator(WordService wordService, WordsBaseFiller filler, TranslateView view) {
        removeAll();
        this.view = view;
        this.wordService = wordService;
        this.filler = filler;
        buildView();
    }

    @Override
    public Composite buildComposite() {
        this.testComposite = buildTest();
        this.testComposite.addFirst(buildTitle());
        return testComposite;
    }

    private TestComposite buildTest() {
        TestComposite testComposite = new TestComposite();

        ButtonsComposite buttonsComposite = new ButtonsComposite(ComponentId.BUTTONS_TEST);
        TestButtonsComponent testButtons = new TestButtonsComponent(this, filler, buttonsComposite);
        DescriptionComponent description = new DescriptionComponent(testButtons);
        RussianWordComponent russianWordComponent = new RussianWordComponent(description);

        russianWordComponent.handleRequest(testComposite, getWords());

        ButtonNextComponent nextButton = new ButtonNextComponent(this);
        nextButton.handleRequest(buttonsComposite, null);

        return testComposite;
    }

    private FourWordsDto getWords() {
        if (view.getDateFrom() != null) {
            return wordService.getFourRandomWordsByDateFrom(view.getDateFrom());
        } else {
            return wordService.getFourRandomWords();
        }
    }

    private TitleComponent buildTitle() {
        TitleComponent titleComponent = new TitleComponent();
        titleComponent.setTitle("Translation from russian to english: Test mode");
        return titleComponent;
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
    }

    @Override
    public void refresh() {
        clean();
        this.testComposite = buildComposite();
        finalComponent = this.testComposite.operation();
        addComponentAsFirst(finalComponent);
    }
}
