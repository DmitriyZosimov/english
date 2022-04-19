package com.myenglish.web.vaadin.ui.newformat.views;

import java.time.LocalDate;

/**
 * Should be used where exist opportunity to select words by date
 */
public interface DateFrom extends View {

    /**
     * to set date
     *
     * @param dateFrom - date that will be used for searching.
     */
    void setDateFrom(LocalDate dateFrom);

    /**
     * to get current saved date that is used for searching.
     *
     * @return current date
     */
    LocalDate getDateFrom();
}
