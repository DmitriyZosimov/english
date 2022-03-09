package com.myenglish.web.vaadin.ui.views;

import com.myenglish.model.Verb;
import com.myenglish.service.VerbService;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.statistics.SimpleStatistic;
import com.myenglish.web.vaadin.ui.utils.DialogTools;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

@PageTitle("Verbs")
@Route(value = "verbs", layout = MainLayout.class)
public class VerbView extends VerticalLayout implements DateFromView {

    private VerbService verbService;
    private VerticalLayout mainLayout;
    private SimpleStatistic simpleStatistic;

    private LocalDate dateFrom;

    @Autowired
    public VerbView(VerbService verbService) {
        this.verbService = verbService;
        createContent();
    }

    @Override
    public void createContent() {
        removeAll();
        add(buildMainLayout(), buildSupportLayout());
    }

    private VerticalLayout buildMainLayout() {
        mainLayout = new VerticalLayout();
        Verb verb = getVerb();

        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.setAlignItems(Alignment.CENTER);
        H3 russian = new H3(verb.getRussian());
        Text description = new Text(verb.getDescription());
        labelLayout.add(russian);
        labelLayout.add(description);

        AtomicBoolean firstForm = new AtomicBoolean(false);
        AtomicBoolean secondForm = new AtomicBoolean(false);
        AtomicBoolean thirdForm = new AtomicBoolean(false);

        HorizontalLayout textFieldLayout = new HorizontalLayout();

        VerticalLayout textFirstFormFieldLayout = new VerticalLayout();
        textFirstFormFieldLayout.setAlignItems(Alignment.CENTER);
        Text v1 = new Text("V1");
        TextField firstFormField = getTextField(verb.getFirstForm(), firstForm);
        firstFormField.setAutofocus(true);
        textFirstFormFieldLayout.add(v1, firstFormField);

        VerticalLayout textSecondFormFieldLayout = new VerticalLayout();
        textSecondFormFieldLayout.setAlignItems(Alignment.CENTER);
        Text v2 = new Text("V2");
        TextField secondFormField = getTextField(verb.getSecondForm(), secondForm);
        textSecondFormFieldLayout.add(v2, secondFormField);

        VerticalLayout textThirdFormFieldLayout = new VerticalLayout();
        textThirdFormFieldLayout.setAlignItems(Alignment.CENTER);
        Text v3 = new Text("V3");
        TextField thirdFormField = getTextField(verb.getThirdForm(), thirdForm);
        textThirdFormFieldLayout.add(v3, thirdFormField);

        textFieldLayout.add(textFirstFormFieldLayout, textSecondFormFieldLayout, textThirdFormFieldLayout);

        Button enterButton = new Button("Enter");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(event -> {
            Icon icon;
            if(firstForm.get() && secondForm.get() && thirdForm.get()) {
                icon = new Icon(VaadinIcon.CHECK);
                icon.setColor("green");
                simpleStatistic.setCount(true);
            } else {
                icon = new Icon(VaadinIcon.CLOSE_SMALL);
                icon.setColor("red");
                simpleStatistic.setCount(false);
            }
            textFieldLayout.add(icon);
        });

        Button answerButton = new Button("get answer");
        answerButton.addClickListener(event -> {
            mainLayout.add(new Text(verb.getFirstForm() + "-" + verb.getSecondForm() + "-" + verb.getThirdForm()));
        });
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(enterButton, answerButton, buildNextButton());
        mainLayout.addAndExpand(labelLayout, textFieldLayout, buttonsLayout);
        mainLayout.setAlignItems(Alignment.CENTER);
        return mainLayout;
    }

    private Div buildNextButton() {
        Div nextButtonDiv = new Div();
        Button button = new Button("next");
        button.addClickShortcut(Key.ARROW_RIGHT);
        button.addClickListener(event -> {
            replace(mainLayout, buildMainLayout());
        });
        nextButtonDiv.add(button);
        return nextButtonDiv;
    }

    private HorizontalLayout buildSupportLayout() {
        HorizontalLayout supportLayout = new HorizontalLayout();

        Button selectDateButton = new Button("Select date");
        selectDateButton.addClickListener(event -> {
            DialogTools.buildDialogForDate(this).open();
        });

        simpleStatistic = new SimpleStatistic(this);

        supportLayout.add(selectDateButton, simpleStatistic);
        return supportLayout;
    }

    private TextField getTextField(String answer, AtomicBoolean firstForm) {
        TextField answerField = new TextField();
        answerField.setWidth(300L, Unit.PIXELS);
        answerField.setClearButtonVisible(true);
        answerField.addValueChangeListener(event -> {
            firstForm.set(answerField.getValue().equals(answer));
        });
        return answerField;
    }

    private Verb getVerb() {
        if(dateFrom != null) {
            return verbService.getRandomVerbByDateFrom(dateFrom).orElse(new Verb());
        } else {
            return verbService.getRandomVerb().orElse(new Verb());
        }
    }

    @Override
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }
}
