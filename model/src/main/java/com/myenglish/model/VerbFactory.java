package com.myenglish.model;

import java.time.LocalDate;

public class VerbFactory {

    private static VerbFactory verbFactory = new VerbFactory();

    private Integer id;
    private String russian;
    private String firstForm;
    private String secondForm;
    private String thirdForm;
    private String description;
    private LocalDate dateOfRegistry;

    public static VerbFactory create() {
        return verbFactory;
    }

    public VerbFactory setId(Integer id) {
        this.id = id;
        return verbFactory;
    }

    public VerbFactory setRussian(String russian) {
        this.russian = russian;
        return verbFactory;
    }

    public VerbFactory setFirstForm(String firstForm) {
        this.firstForm = firstForm;
        return verbFactory;
    }

    public VerbFactory setSecondForm(String secondForm) {
        this.secondForm = secondForm;
        return verbFactory;
    }

    public VerbFactory setThirdForm(String thirdForm) {
        this.thirdForm = thirdForm;
        return verbFactory;
    }

    public VerbFactory setDescription(String description) {
        this.description = description;
        return verbFactory;
    }

    public VerbFactory setDateOfRegistry(LocalDate dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
        return verbFactory;
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
