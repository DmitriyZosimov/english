package com.myenglish.web.vaadin.ui.utils;

import com.myenglish.model.Verb;
import com.myenglish.model.Word;
import com.myenglish.web.vaadin.ui.views.AddingView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.Queue;

/**
 * Class is for generating common buttons
 */
public class ButtonTools {

    /**
     * Build a plus button, which build an additional form layout.
     * @param layout VerticalLayout is in which the form will be added.
     * @param view view
     * @return div component with the plus button
     */
    public static Div createPlusButton(VerticalLayout layout, AddingView view) {
        Div div = new Div();
        Icon icon = new Icon(VaadinIcon.PLUS);
        Button plusButton = new Button(icon);
        plusButton.addClickListener(event ->
                layout.addComponentAtIndex(layout.getComponentCount() - 2, view.createFormLayout()));
        div.add(plusButton);
        return div;
    }

    /**
     * Build a trash button, which remove the form layout and remove the binder from queue of binders.
     * The button is used with forms.
     * @param savedBinders queue of binders for saving
     * @param binder which must be deleted
     * @param formDiv form layout div is which must be deleted
     * @return a div component with trash button
     */
    public static Div createTrashButtonInFormLayout(Queue savedBinders, Binder binder, Div formDiv) {
        Div div = new Div();
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button trash = new Button(icon);
        trash.addClickListener(event -> {
            savedBinders.remove(binder);
            formDiv.removeAll();
        });
        div.add(trash);
        return div;
    }
}
