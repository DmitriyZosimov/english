package com.myenglish.web.vaadin.ui.newformat.views;


import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonPlusComponent;
import com.myenglish.web.vaadin.ui.newformat.views.component.buttons.ButtonSaveComponent;
import com.myenglish.web.vaadin.ui.newformat.views.composite.ButtonsComposite;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.AddWordDecorator;
import com.myenglish.web.vaadin.ui.newformat.views.decorator.Decorator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

/**
 * The view is a Concrete Composite according to Composite design pattern.
 * This contains common components for all decorators that use it.
 */
@org.springframework.stereotype.Component("modifyingView")
@VaadinSessionScope
public class ModifyingView implements View {

    private Component component;
    private Decorator decorator;

    public ModifyingView() {

    }

    @Override
    public Component buildView() {
        if (component == null) {
            throw new NullPointerException("The component is null in ModifyingView");
        }
        return component;
    }

    public void setDecorator(Decorator decorator) {
        this.decorator = decorator;
        buildComponent();
    }

    private void buildComponent() {
        this.component = buildSupportButtons().operation();
    }

    private ButtonsComposite buildSupportButtons() {
        ButtonsComposite supportButtonsComposite = new ButtonsComposite(ComponentId.BUTTONS_SUPPORT);
        ButtonPlusComponent plus = new ButtonPlusComponent(decorator);
        if (decorator instanceof AddWordDecorator) {
            ButtonSaveComponent save = new ButtonSaveComponent(((AddWordDecorator) decorator).getWordService(),
                    ((AddWordDecorator) decorator).getAccumulator());
            supportButtonsComposite.add(save);
        }
        supportButtonsComposite.addFirst(plus);
        return supportButtonsComposite;
    }
}
