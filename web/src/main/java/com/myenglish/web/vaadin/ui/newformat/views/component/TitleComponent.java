package com.myenglish.web.vaadin.ui.newformat.views.component;

import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

/**
 * Title component. Text will be written using H1 html element.
 */
public class TitleComponent implements VaadinComponent {

    private String title;
    private String id;

    public TitleComponent() {
        this.id = ComponentId.TITLE;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Component operation() {
        Div div = new Div();
        H1 titleText = new H1(this.title);
        div.setId(this.id);
        div.add(titleText);
        div.setWidthFull();
        div.getElement().getStyle().set("text-align", "center");
        return div;
    }
}
