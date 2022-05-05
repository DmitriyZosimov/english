package com.myenglish.web.vaadin.ui.newformat.views;

import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonInputModeComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonSelectDateComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonTestModeComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.BodyComposite;
import com.myenglish.web.vaadin.ui.newformat.views.composite.ButtonsComposite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import java.time.LocalDate;

/**
 * It's a view of translation from russian and also is a concrete component of a decorate design pattern.
 */
@org.springframework.stereotype.Component
@VaadinSessionScope
public class TranslateView implements View, DateFrom {

    private Component component;
    private LocalDate dateFrom;

    public TranslateView() {
        BodyComposite bodyComposite = new BodyComposite();
        bodyComposite.add(buildSupportButtons());
        this.component = bodyComposite.operation();
    }

    @Override
    public Component buildView() {
        return component;
    }

    @Override
    public void clean() {

    }

    @Override
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    private ButtonsComposite buildSupportButtons() {
        ButtonsComposite supportButtonsComposite = new ButtonsComposite(ComponentId.BUTTONS_SUPPORT);
        ButtonSelectDateComponent selectDate = new ButtonSelectDateComponent(this);
        ButtonTestModeComponent testMode = new ButtonTestModeComponent();
        ButtonInputModeComponent inputMode = new ButtonInputModeComponent();
        supportButtonsComposite.add(selectDate, testMode, inputMode);
        return supportButtonsComposite;
    }
}
