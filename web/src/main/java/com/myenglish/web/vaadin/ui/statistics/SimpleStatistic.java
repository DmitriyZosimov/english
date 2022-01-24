package com.myenglish.web.vaadin.ui.statistics;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SimpleStatistic extends Button {

    private static final String TITLE = "Your statistic";
    private static final String RIGHT_ANSWERS = "right answers";
    private static final String ALL_ANSWERS = "all answers";
    private static final String PERCENT = "percent";

    private int right;
    private int all;
    private int percent;

    VerticalLayout statisticLayout;

    public SimpleStatistic(VerticalLayout layout) {
        statisticLayout = new VerticalLayout();
        createContent(layout);
    }

    private void createContent(VerticalLayout layout) {
        setText("Open statistic");
        addClickListener(event -> {
            layout.remove(statisticLayout);
            statisticLayout.add(new Text(TITLE),
                    buildRow(right, all, percent));
            layout.add(statisticLayout);
        });
    }

    private Div buildRow(int right, int all, int percent) {
        String row = String.format("%s:%40d%n%s:%40d%n%s:%40d%s", RIGHT_ANSWERS, right, ALL_ANSWERS, all,
                PERCENT, percent, "%");
        Text text = new Text(row);
        Div div = new Div(text);
        return div;
    }

    public void setCount(boolean correct) {
        if(correct) {
            right++;
        }
        all++;
        percent = right/all*100;
    }

}
