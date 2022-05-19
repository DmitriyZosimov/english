package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.web.vaadin.ui.newformat.views.BinderAccumulator;
import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * Contains a vaadin component {@link Button}. It is used for saving incoming words in DB.
 * After clicking on this button, input words send to {@link WordService} from {@link BinderAccumulator}.
 */
public class ButtonSaveComponent implements VaadinComponent {

    private WordService wordService;
    private BinderAccumulator<Word> accumulator;

    public ButtonSaveComponent(WordService wordService, BinderAccumulator<Word> accumulator) {
        this.wordService = wordService;
        this.accumulator = accumulator;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        Button button = new Button(ButtonsNames.SAVE);
        button.addClickListener(event -> {
            for (Binder<Word> binder : accumulator.getAccumulator()) {
                Word word = new Word();
                try {
                    binder.writeBean(word);
                    Word savedWord = wordService.saveOrUpdateWord(word);
                    Text text;
                    if (savedWord.getId() != null) {
                        text = new Text(savedWord.getEnglish() + " was saved; ");
                    } else {
                        text = new Text(savedWord.getEnglish() + " was not saved; ");
                        text.getElement().getStyle().set("color", "red");
                    }
                    div.add(text);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }
        });
        div.add(button);
        return div;
    }
}
