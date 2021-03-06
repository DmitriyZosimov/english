package com.myenglish.web.vaadin.ui.views;

import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import com.myenglish.service.filters.WordsBaseFiller;
import com.myenglish.web.vaadin.ui.MainLayout;
import com.myenglish.web.vaadin.ui.utils.DialogTools;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@PageTitle("Translate from russian")
@Route(value = "translate/ru", layout = MainLayout.class)
public class TranslateFromRussianView extends VerticalLayout implements DateFromView {

    private WordService wordService;
    private WordsBaseFiller filler;

    private VerticalLayout testModeMainLayout;
    private VerticalLayout inputModeMainLayout;

    private LocalDate dateFrom;
    private boolean isTestMode = true;

    @Autowired
    public TranslateFromRussianView(WordService wordService, WordsBaseFiller filler) {
        this.wordService = wordService;
        this.filler = filler;
        createContent();
    }

    @Override
    public void createContent() {
        removeAll();
        add(buildTestModeMainLayout(), buildSupportLayout());
    }

    private VerticalLayout buildTestModeMainLayout() {
        testModeMainLayout = new VerticalLayout();
        FourWordsDto dto = getWordsList();
        List<Word> words = dto.getFourRandomWords();
        Word word = dto.getCorrectWord();

        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.setAlignItems(Alignment.CENTER);
        H3 russian = new H3(word.getRussian());
        Text description = new Text(word.getDescription());
        labelLayout.add(russian);
        labelLayout.add(description);

        Div buttonsDiv = new Div();
        for (Word wordForButton : words) {
            Div singletonButtonDiv = new Div();
            Label buttonLabel = new Label(wordForButton.getEnglish());
            Button wordButton = new Button(buttonLabel);
            wordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            wordButton.isDisableOnClick();
            wordButton.setWidth(450f, Unit.PIXELS);
            wordButton.setHeight(50f, Unit.PIXELS);
            wordButton.addClickListener(event -> {
                if (word.equals(wordForButton)) {
                    wordButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    filler.fill(word);
                    replace(testModeMainLayout, buildTestModeMainLayout());
                } else {
                    wordButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    filler.remove(word);
                }
                wordButton.removeThemeVariants(ButtonVariant.LUMO_CONTRAST);
            });
            singletonButtonDiv.add(wordButton);
            buttonsDiv.add(singletonButtonDiv);
        }

        testModeMainLayout.add(labelLayout, buttonsDiv, buildNextButton());
        testModeMainLayout.setAlignItems(Alignment.CENTER);
        return testModeMainLayout;
    }

    private VerticalLayout buildInputModeMainLayout() {
        inputModeMainLayout = new VerticalLayout();
        Word word = getWord();
        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.setAlignItems(Alignment.CENTER);
        H3 russian = new H3(word.getRussian());
        Text description = new Text(word.getDescription());
        labelLayout.add(russian);
        labelLayout.add(description);

        AtomicBoolean result = new AtomicBoolean(false);

        HorizontalLayout textFieldLayout = new HorizontalLayout();
        TextField answerField = new TextField();
        answerField.setAutofocus(true);
        answerField.setWidth(500L, Unit.PIXELS);
        answerField.setClearButtonVisible(true);
        answerField.addValueChangeListener(event -> result.set(answerField.getValue().equals(word.getEnglish())));
        textFieldLayout.add(answerField);

        Button enterButton = new Button("Enter");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(event -> {
            Icon icon;
            if (result.get()) {
                icon = new Icon(VaadinIcon.CHECK);
                icon.setColor("green");
                filler.fill(word);
            } else {
                icon = new Icon(VaadinIcon.CLOSE_SMALL);
                icon.setColor("red");
                filler.remove(word);
            }
            textFieldLayout.add(icon);
        });

        Button answerButton = new Button("get answer");
        answerButton.addClickListener(event -> {
            VerticalLayout answerLayout = new VerticalLayout(
                    new Text(word.getEnglish()),
                    prepareTranscription(word.getTranscription()));
            answerLayout.setAlignItems(Alignment.CENTER);
            answerLayout.setMargin(false);
            inputModeMainLayout.add(answerLayout);
        });
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(enterButton, answerButton, buildNextButton());
        inputModeMainLayout.addAndExpand(labelLayout, textFieldLayout, buttonsLayout);
        inputModeMainLayout.setAlignItems(Alignment.CENTER);
        return inputModeMainLayout;
    }

    private Div buildNextButton() {
        Div nextButtonDiv = new Div();
        Button button = new Button("next");
        button.addClickShortcut(Key.ARROW_RIGHT);
        button.addClickListener(event -> {
            if (isTestMode) {
                replace(testModeMainLayout, buildTestModeMainLayout());
            } else {
                replace(inputModeMainLayout, buildInputModeMainLayout());
            }
        });
        nextButtonDiv.add(button);
        return nextButtonDiv;
    }

    private HorizontalLayout buildSupportLayout() {
        HorizontalLayout supportLayout = new HorizontalLayout();

        Button selectDateButton = new Button("Select date");
        selectDateButton.addClickListener(event -> DialogTools.buildDialogForDate(this).open());

        Button testModeButton = new Button("Test mode");
        Button inputModeButton = new Button("Input mode");

        testModeButton.setEnabled(false);
        testModeButton.addClickListener(event -> {
            isTestMode = true;
            replace(inputModeMainLayout, buildTestModeMainLayout());
            inputModeButton.setEnabled(true);
            testModeButton.setEnabled(false);
        });

        inputModeButton.addClickListener(event -> {
            isTestMode = false;
            replace(testModeMainLayout, buildInputModeMainLayout());
            testModeButton.setEnabled(true);
            inputModeButton.setEnabled(false);
        });

        supportLayout.add(selectDateButton, testModeButton, inputModeButton);
        return supportLayout;
    }

    private FourWordsDto getWordsList() {
        if (dateFrom != null) {
            return wordService.getFourRandomWordsByDateFrom(dateFrom);
        } else {
            return wordService.getFourRandomWords();
        }
    }

    private Word getWord() {
        if (dateFrom != null) {
            return wordService.getRandomWordByDateFrom(dateFrom).orElse(new Word());
        } else {
            return wordService.getRandomWord().orElse(new Word());
        }
    }

    @Override
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    private Div prepareTranscription(String transcription) {
        Text text;
        if (transcription != null) {
            text = new Text(transcription);
        } else {
            text = new Text("no transcription");
        }
        Div transcriptionDiv = new Div(text);
        transcriptionDiv.getStyle().set("color", "grey");
        return transcriptionDiv;
    }
}
