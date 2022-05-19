package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.model.Word;
import com.myenglish.web.vaadin.ui.newformat.views.BinderAccumulator;
import com.myenglish.web.vaadin.ui.newformat.views.LabelNames;
import com.myenglish.web.vaadin.ui.newformat.views.PlaceholderNames;
import com.myenglish.web.vaadin.ui.newformat.views.ValidatePredicate;
import com.myenglish.web.vaadin.ui.newformat.views.composite.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializablePredicate;

/**
 * Form contains only english, russian, transcription fields and a description area.
 * The Form is used for inputting new words. The form can be added to any Composite for further sequential
 * creating Components.
 * BinderAccumulator collects the Word forms before they will be saved.
 * New Form can be created by PlusButton. Any created form can be removed by TrashButton.
 * English and Russian fields can't be null and conform {@link ValidatePredicate} patterns.
 * Description and Transcription can be null.
 */
public class FormWordComponent extends FormLayout implements VaadinComponent, ChainOfResponsibilityHandler, ValidatePredicate {

    private ChainOfResponsibilityHandler nextHandler;
    private BinderAccumulator<Word> accumulator;
    private Binder<Word> binder;

    public FormWordComponent(ChainOfResponsibilityHandler nextHandler, BinderAccumulator<Word> accumulator) {
        this.nextHandler = nextHandler;
        this.accumulator = accumulator;
    }

    @Override
    public void handleRequest(Composite composite, Object word) {
        composite.add(this);
        nextHandler.handleRequest(composite, word);
    }

    @Override
    public Component operation() {
        binder = new Binder<>();

        TextField english = buildEnglishField();
        TextField russian = buildRussianField();
        TextArea description = buildDescription();
        TextField transcription = buildTranscriptionField();

        addFormItem(english, LabelNames.ENGLISH);
        addFormItem(russian, LabelNames.RUSSIAN);
        addFormItem(description, LabelNames.DESCRIPTION);
        addFormItem(transcription, LabelNames.TRANSCRIPTION);

        SerializablePredicate<String> englishPatternPredicate = buildEnglishPatternPredicate();
        SerializablePredicate<String> russianPatternPredicate = buildRussianPatternPredicate();

        Binder.Binding<Word, String> englishBinding = binder.forField(english)
                .withValidator(buildCheckEmptyTextField(english), Message.NOT_EMPTY.getMessage())
                .withValidator(englishPatternPredicate, Message.INCORRECT.getMessage())
                .bind(Word::getEnglish, Word::setEnglish);

        Binder.Binding<Word, String> russianBinding = binder.forField(russian)
                .withValidator(buildCheckEmptyTextField(russian), Message.NOT_EMPTY.getMessage())
                .withValidator(russianPatternPredicate, Message.INCORRECT.getMessage())
                .bind(Word::getRussian, Word::setRussian);

        binder.forField(description).bind(Word::getDescription, Word::setDescription);
        binder.forField(transcription).bind(Word::getTranscription, Word::setTranscription);

        english.addValueChangeListener(event -> englishBinding.validate());
        russian.addValueChangeListener(event -> russianBinding.validate());

        accumulator.getAccumulator().add(binder);

        addDetachListener(event -> {
            accumulator.getAccumulator().remove(binder);
        });
        return this;
    }

    public void removeBinderFromAccumulator() {
        if (binder != null) {
            this.accumulator.getAccumulator().remove(binder);
        }
    }

    private TextField buildEnglishField() {
        TextFieldComponent englishField = new TextFieldComponent();
        return (TextField) englishField.operation();
    }

    private TextField buildRussianField() {
        TextFieldComponent russianField = new TextFieldComponent();
        return (TextField) russianField.operation();
    }

    private TextArea buildDescription() {
        TextAreaComponent descriptionArea = new TextAreaComponent();
        descriptionArea.setPlaceholder(PlaceholderNames.ENTER_DESCRIPTION);
        return (TextArea) descriptionArea.operation();
    }

    private TextField buildTranscriptionField() {
        TextFieldComponent transcriptionField = new TextFieldComponent();
        transcriptionField.setPlaceholder(PlaceholderNames.ENTER_TRANSCRIPTION);
        return (TextField) transcriptionField.operation();
    }
}
