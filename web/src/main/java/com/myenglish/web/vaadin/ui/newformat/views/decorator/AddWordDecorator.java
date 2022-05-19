package com.myenglish.web.vaadin.ui.newformat.views.decorator;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.newformat.views.BinderAccumulator;
import com.myenglish.web.vaadin.ui.newformat.views.ModifyingView;
import com.myenglish.web.vaadin.ui.newformat.views.View;
import com.myenglish.web.vaadin.ui.newformat.views.component.FormWordComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonTrash;
import com.myenglish.web.vaadin.ui.newformat.views.composite.FormLayoutComposite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Main decorator for adding new words. This decorator uses {@link ModifyingView} as Concrete Component.
 * The decorator creates a form layout and adds support's buttons from {@link ModifyingView}.
 * The decorator is used as a vertical layout. All vaadin components will be added to this decorator, even elements
 * from {@link ModifyingView}.
 * The decorator contains link to {@link WordService} for further send to {@link com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonSaveComponent}
 * using ModifyingView like a mediator.
 * {@link BinderAccumulator} is used for collecting words that will be saved in db. This component send to ButtonSaveComponent
 * as well.
 * First of all created form layout will be added in {@code finalComponent} and then the finalComponent will be added to
 * decorator's layout.
 */
@PageTitle("Add new words")
@Route(value = "word/new", layout = MainLayout.class)
@VaadinSessionScope
public class AddWordDecorator extends VerticalLayout implements Decorator {

    private BinderAccumulator<Word> accumulator = new BinderAccumulator<>();
    private WordService wordService;
    private VerticalLayout finalComponent = new VerticalLayout();

    public AddWordDecorator(@Qualifier("modifyingView") View view, WordService wordService) {
        this.wordService = wordService;
        ((ModifyingView) view).setDecorator(this);
        buildView();
        add(finalComponent, view.buildView());
    }

    @Override
    public Component buildView() {
        finalComponent.add(createFormLayout());
        return finalComponent;
    }

    public WordService getWordService() {
        return this.wordService;
    }

    public BinderAccumulator<Word> getAccumulator() {
        return this.accumulator;
    }

    private Div createFormLayout() {
        Div div = new Div();
        FormLayoutComposite formLayoutComposite = new FormLayoutComposite();
        formLayoutComposite.add(new FormWordComponent(null, accumulator), new ButtonTrash());
        div.add(formLayoutComposite.operation());
        return div;
    }
}
