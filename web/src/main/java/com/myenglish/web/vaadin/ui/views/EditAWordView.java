package com.myenglish.web.vaadin.ui.views;

import com.myenglish.web.vaadin.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Edit a word")
@Route(value = "word/edit", layout = MainLayout.class)
public class EditAWordView extends VerticalLayout implements View {

    @Override
    public void createContent() {

    }
}
