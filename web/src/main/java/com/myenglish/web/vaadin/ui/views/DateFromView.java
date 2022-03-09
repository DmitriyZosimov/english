package com.myenglish.web.vaadin.ui.views;

import java.time.LocalDate;

/**
 * Should be used where exist opportunity to select words by date
 */
public interface DateFromView extends View {

    void setDateFrom(LocalDate dateFrom);
}
