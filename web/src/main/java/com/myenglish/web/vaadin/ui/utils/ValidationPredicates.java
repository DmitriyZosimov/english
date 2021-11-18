package com.myenglish.web.vaadin.ui.utils;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationPredicates {

    public static SerializablePredicate<String> buildEnglishPatternPredicate() {
        SerializablePredicate<String> englishPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[a-zA-Z' -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };
        return englishPatternPredicate;
    }

    public static SerializablePredicate<String> buildRussianPatternPredicate() {
        SerializablePredicate<String> russianPatternPredicate = value -> {
            Pattern pattern = Pattern.compile("[а-яА-Я -]+");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        };
        return russianPatternPredicate;
    }

    public static SerializablePredicate<String> buildCheckEmptyTextField(TextField textField) {
        SerializablePredicate<String> englishOrRussianPredicate = value -> !textField
                .getValue().trim().isEmpty();
        return englishOrRussianPredicate;
    }
}
