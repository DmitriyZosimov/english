package com.myenglish.model;

import java.time.LocalDate;

public class VerbBuilder {

    private static VerbBuilder verbBuilder = new VerbBuilder();

    private Integer id;
    private String russian;
    private String firstForm;
    private String secondForm;
    private String thirdForm;
    private String description;
    private LocalDate dateOfRegistry;

    private VerbBuilder() {
    }

    public static VerbBuilder create() {
        return verbBuilder;
    }

    public VerbBuilder setId(Integer id) {
        this.id = id;
        return verbBuilder;
    }

    public VerbBuilder setRussian(String russian) {
        this.russian = russian;
        return verbBuilder;
    }

    public VerbBuilder setFirstForm(String firstForm) {
        this.firstForm = firstForm;
        return verbBuilder;
    }

    public VerbBuilder setSecondForm(String secondForm) {
        this.secondForm = secondForm;
        return verbBuilder;
    }

    public VerbBuilder setThirdForm(String thirdForm) {
        this.thirdForm = thirdForm;
        return verbBuilder;
    }

    public VerbBuilder setDescription(String description) {
        this.description = description;
        return verbBuilder;
    }

    public VerbBuilder setDateOfRegistry(LocalDate dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
        return verbBuilder;
    }

    public Verb build() {
        Verb verb = new Verb();
        verb.setId(id);
        verb.setRussian(russian);
        verb.setFirstForm(firstForm);
        verb.setSecondForm(secondForm);
        verb.setThirdForm(thirdForm);
        verb.setDescription(description);
        verb.setDateOfRegistry(dateOfRegistry);
        return verb;
    }

}
