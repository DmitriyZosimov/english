package com.myenglish.web.vaadin.ui.newformat.views.composite;

import com.myenglish.web.vaadin.ui.newformat.views.ComponentId;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;

/**
 * The composite contains primary components of test decorator like
 * {@link com.myenglish.web.vaadin.ui.newformat.views.component.RussianWordComponent},
 * {@link com.myenglish.web.vaadin.ui.newformat.views.component.DescriptionComponent},
 * {@link com.myenglish.web.vaadin.ui.newformat.views.component.TestButtonsComponent},
 * {@link ButtonsComposite}
 */
public class TestComposite extends AbstractComposite {

    public TestComposite() {
        super();
        setId(ComponentId.WORD_TEST);
        ((FlexComponent) getLayout()).setAlignItems(FlexComponent.Alignment.CENTER);
        ((ThemableLayout) getLayout()).setMargin(false);
    }
}
