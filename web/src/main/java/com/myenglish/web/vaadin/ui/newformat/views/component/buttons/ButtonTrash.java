package com.myenglish.web.vaadin.ui.newformat.views.component.buttons;

import com.myenglish.web.vaadin.ui.newformat.views.component.FormWordComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.VaadinComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.Optional;

/**
 * Contains a vaading component {@link Button}. It is used for deleting existing layout from parents.
 */
public class ButtonTrash implements VaadinComponent {

    @Override
    public Component operation() {
        Div div = new Div();
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button trash = new Button(icon);
        trash.addClickListener(event -> {
            if (div.getParent().isPresent()) {
                Optional<Component> formWordComponent = div.getParent().get().getChildren()
                        .filter(component -> component instanceof FormWordComponent).findFirst();
                formWordComponent.ifPresent(component -> ((FormWordComponent) component).removeBinderFromAccumulator());
                div.getParent().get().getParent().ifPresent(component -> component.getElement().removeFromParent());
            }
        });
        div.add(trash);
        return div;
    }
}
