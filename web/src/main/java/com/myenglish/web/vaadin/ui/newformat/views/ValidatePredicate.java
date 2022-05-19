package com.myenglish.web.vaadin.ui.newformat.views;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains predications for validation inputting words.
 */
public interface ValidatePredicate {

    default SerializablePredicate<String> buildEnglishPatternPredicate() {
        SerializablePredicate<String> englishPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[a-zA-Z' -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };
        return englishPatternPredicate;
    }

    default SerializablePredicate<String> buildRussianPatternPredicate() {
        SerializablePredicate<String> russianPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[а-яА-Я -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };
        return russianPatternPredicate;
    }

    default SerializablePredicate<String> buildCheckEmptyTextField(TextField textField) {
        SerializablePredicate<String> englishOrRussianPredicate = value -> !textField
                .getValue().trim().isEmpty();
        return englishOrRussianPredicate;
    }

    /**
     * Enum with possible messages that can be displayed after validation fields.
     */
    public enum Message {
        NOT_EMPTY("The field must not be null"),
        INCORRECT("The word was write incorrectly");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

    }
}
