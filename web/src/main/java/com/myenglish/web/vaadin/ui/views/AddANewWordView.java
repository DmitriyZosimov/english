package com.myenglish.web.vaadin.ui.views;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.utils.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.Queue;


@PageTitle("Add a new word")
@Route(value = "word/new", layout = MainLayout.class)
public class AddANewWordView extends VerticalLayout implements AddingView {

    private static final String MAIN_LABEL = "You can add a new words here";

    private WordService wordService;
    private Queue<Binder<Word>> savedBinders;

    @Autowired
    public AddANewWordView(WordService wordService) {
        this.wordService = wordService;
        this.savedBinders = new LinkedList<>();
        createContent();
    }

    @Override
    public void createContent() {
        add(LabelTools.createMainLabel(MAIN_LABEL),
                createFormLayout(),
                ButtonTools.createPlusButton(this, this),
                createSaveButton());
    }

    @Override
    public Div createFormLayout() {
        Div div = new Div();

        TextField englishField = TextFieldTools.buildTextField();
        TextField russianField = TextFieldTools.buildTextField();

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPlaceholder("write a description (optional)");
        descriptionArea.setClearButtonVisible(true);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Word> binder = new Binder<>();

        layoutWithBinder.addFormItem(englishField, "english word");
        layoutWithBinder.addFormItem(russianField, "russian translation");
        layoutWithBinder.addFormItem(descriptionArea, "description");

        SerializablePredicate<String> englishPatternPredicate = ValidationPredicates.buildEnglishPatternPredicate();
        SerializablePredicate<String> russianPatternPredicate = ValidationPredicates.buildRussianPatternPredicate();

        Binder.Binding<Word, String> englishBinding = binder.forField(englishField)
                .withValidator(ValidationPredicates.buildCheckEmptyTextField(englishField), "The field must not be null")
                .withValidator(englishPatternPredicate, "The word was write incorrectly")
                .bind(Word::getEnglish, Word::setEnglish);

        Binder.Binding<Word, String> russianBinding = binder.forField(russianField)
                .withValidator(ValidationPredicates.buildCheckEmptyTextField(russianField), "The field must not be null")
                .withValidator(russianPatternPredicate, "The word was write incorrectly")
                .bind(Word::getRussian, Word::setRussian);

        binder.forField(descriptionArea)
                .bind(Word::getDescription, Word::setDescription);

        englishField.addValueChangeListener(event -> englishBinding.validate());
        russianField.addValueChangeListener(event -> russianBinding.validate());

        savedBinders.add(binder);

        Div trashButtonDiv = ButtonTools.createTrashButtonInFormLayout(savedBinders, binder, div);

        HorizontalLayout inputHorizontalLayout = new HorizontalLayout();
        inputHorizontalLayout.addAndExpand(layoutWithBinder, trashButtonDiv);
        div.add(inputHorizontalLayout);
        return div;
    }

    private Div createSaveButton() {
        Div div = new Div();
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            for(Binder<Word> binder : savedBinders) {
                Word word = new Word();
                try {
                    binder.writeBean(word);
                    Word savedWord = wordService.saveOrUpdateWord(word);
                    Text text;
                    if(savedWord.getId() != null) {
                        text = new Text(savedWord.getEnglish() + " was saved");
                    } else {
                        text = new Text(savedWord.getEnglish() + " was not saved");
                    }
                    div.add(text);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }
        });
        div.add(saveButton);
        return div;
    }
}
