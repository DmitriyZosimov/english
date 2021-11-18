package com.myenglish.web.vaadin.ui.utils;

import com.myenglish.web.vaadin.ui.views.View;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class is for building common dialogs
 */
public class DialogTools {

    /**
     * Build the dialog for choosing date.
     * @param dateFrom field is for entering
     * @param view where the dialog is used
     * @return dialog component
     */
    public static Dialog buildDialogForDate(LocalDate dateFrom, View view) {
        AtomicReference<LocalDate> date = new AtomicReference<>(dateFrom);
        Dialog dialog = new Dialog();
        dialog.add(new Text("You can choose date from when words are added."));
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);

        DatePicker datePicker = new DatePicker();
        Button confirmButton = new Button("Confirm", event -> {
            date.set(datePicker.getValue());
            dialog.close();
            view.createContent();
        });

        Button cancelButton = new Button("Cancel", event -> {
            dialog.close();
        });

        dialog.add(new Div(datePicker), new Div( confirmButton, cancelButton));
        return dialog;
    }
}
