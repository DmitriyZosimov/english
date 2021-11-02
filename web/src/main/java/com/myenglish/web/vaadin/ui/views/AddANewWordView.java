package com.myenglish.web.vaadin.ui.views;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@PageTitle("Add a new word")
@Route(value = "word/new", layout = MainLayout.class)
public class AddANewWordView extends VerticalLayout {

    private WordService wordService;
    private Queue<Binder<Word>> savedBinders;

    @Autowired
    public AddANewWordView(WordService wordService) {
        this.wordService = wordService;
        this.savedBinders = new LinkedList<>();
        createContent();
    }

    protected void createContent() {
        add(createMainLabel(), createFormLayout(), createPlusButton(), createSaveButton());
    }

    private Div createFormLayout() {
        Div div = new Div();

        TextField englishField = buildTextField();
        TextField russianField = buildTextField();

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPlaceholder("write a description (optional)");
        descriptionArea.setClearButtonVisible(true);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Word> binder = new Binder<>();

        layoutWithBinder.addFormItem(englishField, "english word");
        layoutWithBinder.addFormItem(russianField, "russian translation");
        layoutWithBinder.addFormItem(descriptionArea, "description");

        SerializablePredicate<String> englishOrRussianPredicate = value -> !englishField
                .getValue().trim().isEmpty()
                || !russianField.getValue().trim().isEmpty();

        SerializablePredicate<String> englishPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[a-zA-Z' -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };

        SerializablePredicate<String> russianPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[а-яА-Я -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };

        Binder.Binding<Word, String> englishBinding = binder.forField(englishField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(englishPatternPredicate, "The word was write incorrectly")
                .bind(Word::getEnglish, Word::setEnglish);

        Binder.Binding<Word, String> russianBinding = binder.forField(russianField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(russianPatternPredicate, "The word was write incorrectly")
                .bind(Word::getRussian, Word::setRussian);

        binder.forField(descriptionArea)
                .bind(Word::getDescription, Word::setDescription);

        englishField.addValueChangeListener(event -> englishBinding.validate());
        russianField.addValueChangeListener(event -> russianBinding.validate());

        savedBinders.add(binder);

        Icon icon = new Icon(VaadinIcon.TRASH);
        Button trash = new Button(icon);
        trash.addClickListener(event -> {
            if(savedBinders.contains(binder)) {
                savedBinders.remove(binder);
            }
            div.removeAll();
        });
        HorizontalLayout inputHorizontalLayout = new HorizontalLayout();
        inputHorizontalLayout.addAndExpand(layoutWithBinder, trash);
        div.add(inputHorizontalLayout);
        return div;
    }

    private TextField buildTextField() {
        TextField textField = new TextField();
        textField.setPlaceholder("write a word");
        textField.setClearButtonVisible(true);
        textField.setWidth("400");
        return textField;
    }

    private Div createPlusButton() {
        Div div = new Div();
        Icon icon = new Icon(VaadinIcon.PLUS);
        Button plusButton = new Button(icon);
        plusButton.addClickListener(event -> addComponentAtIndex(getComponentCount() - 2, createFormLayout()));
        div.add(plusButton);
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

    private Div createMainLabel() {
        Div div = new Div();
        H2 text = new H2("You can add new words here");
        div.add(text);
        return div;
    }
}
