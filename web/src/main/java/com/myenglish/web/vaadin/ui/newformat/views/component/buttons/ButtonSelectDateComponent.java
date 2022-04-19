package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.ButtonsNames;
import com.myenglish.web.vaadin.ui.newformat.views.DateFrom;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;

/**
 * Creates dialog to select date that will be used for searching words which were saved after this date.
 */
public class ButtonSelectDateComponent implements VaadinComponent {

    private DateFrom dateFromComponent;

    public ButtonSelectDateComponent(DateFrom dateFromComponent) {
        this.dateFromComponent = dateFromComponent;
    }

    @Override
    public Component operation() {
        Button selectDateButton = new Button(ButtonsNames.SELECT_DATE);
        selectDateButton.addClickListener(event -> buildDialogForDate().open());
        return selectDateButton;
    }

    private Dialog buildDialogForDate() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("You can choose date from when words are added."));
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        Button confirmButton = new Button("Confirm", event -> {
            dateFromComponent.setDateFrom(datePicker.getValue());
            dialog.close();
            dateFromComponent.buildView();
        });
        confirmButton.setAutofocus(true);

        Button cancelButton = new Button("Cancel", event -> {
            dialog.close();
        });

        dialog.add(new Div(datePicker), new Div(confirmButton, cancelButton));
        return dialog;
    }

}
