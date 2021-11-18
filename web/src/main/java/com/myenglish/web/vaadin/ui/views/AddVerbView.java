package com.myenglish.web.vaadin.ui.views;

import com.myenglish.model.Verb;
import com.myenglish.service.VerbService;
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

@PageTitle("Add a new verb")
@Route(value = "verbs/new", layout = MainLayout.class)
public class AddVerbView extends VerticalLayout {

    private VerbService verbService;
    private Queue<Binder<Verb>> savedBinders;

    @Autowired
    public AddVerbView(VerbService verbService) {
        this.verbService = verbService;
        this.savedBinders = new LinkedList<>();
        createContent();
    }

    protected void createContent() {
        add(createMainLabel(), createFormLayout(), createPlusButton(), createSaveButton());
    }

    private Div createFormLayout() {
        Div div = new Div();

        TextField firstFormField = buildTextField();
        TextField secondFormField = buildTextField();
        TextField thirdFormField = buildTextField();
        TextField russianField = buildTextField();

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPlaceholder("write a description (optional)");
        descriptionArea.setClearButtonVisible(true);

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Verb> binder = new Binder<>();

        layoutWithBinder.addFormItem(russianField, "Russian translation");
        layoutWithBinder.addFormItem(firstFormField, "Infinitive");
        layoutWithBinder.addFormItem(secondFormField, "Past Simple");
        layoutWithBinder.addFormItem(thirdFormField, "Past Participle");
        layoutWithBinder.addFormItem(descriptionArea, "description");

        SerializablePredicate<String> englishOrRussianPredicate = value -> !firstFormField
                .getValue().trim().isEmpty()
                || !secondFormField.getValue().trim().isEmpty()
                || !thirdFormField.getValue().trim().isEmpty()
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

        Binder.Binding<Verb, String> firstFormBinding = binder.forField(firstFormField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(englishPatternPredicate, "The word was write incorrectly")
                .bind(Verb::getFirstForm, Verb::setFirstForm);

        Binder.Binding<Verb, String> secondFormBinding = binder.forField(secondFormField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(englishPatternPredicate, "The word was write incorrectly")
                .bind(Verb::getSecondForm, Verb::setSecondForm);

        Binder.Binding<Verb, String> thridFormBinding = binder.forField(thirdFormField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(englishPatternPredicate, "The word was write incorrectly")
                .bind(Verb::getThirdForm, Verb::setThirdForm);

        Binder.Binding<Verb, String> russianBinding = binder.forField(russianField)
                .withValidator(englishOrRussianPredicate, "The field must not be null")
                .withValidator(russianPatternPredicate, "The word was write incorrectly")
                .bind(Verb::getRussian, Verb::setRussian);

        binder.forField(descriptionArea)
                .bind(Verb::getDescription, Verb::setDescription);

        firstFormField.addValueChangeListener(event -> firstFormBinding.validate());
        secondFormField.addValueChangeListener(event -> secondFormBinding.validate());
        thirdFormField.addValueChangeListener(event -> thridFormBinding.validate());
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
            for(Binder<Verb> binder : savedBinders) {
                Verb verb = new Verb();
                try {
                    binder.writeBean(verb);
                    Verb savedVerb = verbService.saveOrUpdateVerb(verb);
                    Text text;
                    if(savedVerb.getId() != null) {
                        text = new Text(savedVerb.getFirstForm() + " was saved");
                    } else {
                        text = new Text(savedVerb.getFirstForm() + " was not saved");
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
        H2 text = new H2("You can add a new verb here");
        div.add(text);
        return div;
    }
}
