package com.myenglish.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "verbs")
@Data
public class Verb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;
    @Column(name = "russian", nullable = false)
    private String russian;
    @Column(name = "first_form", nullable = false)
    private String firstForm;
    @Column(name = "second_form", nullable = false)
    private String secondForm;
    @Column(name = "third_form", nullable = false)
    private String thirdForm;
    @Column(name = "description")
    private String description;
    @Column(name = "date_of_registry")
    private LocalDate dateOfRegistry;

    @PrePersist
    public void setupDate() {
        dateOfRegistry = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("%s%s%15s%15s%15s%n%s%n", "Verb:", russian, firstForm, secondForm, thirdForm, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Verb verb = (Verb) o;

        if (id != null ? !id.equals(verb.id) : verb.id != null) return false;
        if (!russian.equals(verb.russian)) return false;
        if (!firstForm.equals(verb.firstForm)) return false;
        if (!secondForm.equals(verb.secondForm)) return false;
        if (!thirdForm.equals(verb.thirdForm)) return false;
        if (description != null ? !description.equals(verb.description) : verb.description != null) return false;
        return dateOfRegistry != null ? dateOfRegistry.equals(verb.dateOfRegistry) : verb.dateOfRegistry == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + russian.hashCode();
        result = 31 * result + firstForm.hashCode();
        result = 31 * result + secondForm.hashCode();
        result = 31 * result + thirdForm.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateOfRegistry != null ? dateOfRegistry.hashCode() : 0);
        return result;
    }
}
