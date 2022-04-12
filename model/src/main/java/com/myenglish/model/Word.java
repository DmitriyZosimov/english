package com.myenglish.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "words")
@Data
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, unique = true, updatable = false)
    private Integer id;
    @Column(name = "english", nullable = false)
    private String english;
    @Column(name = "russian", nullable = false)
    private String russian;
    @Column(name = "description")
    private String description;
    @Column(name = "date_of_registry")
    private LocalDate dateOfRegistry;
    @Column(name = "transcription")
    private String transcription;

    @PrePersist
    public void setupDate() {
        dateOfRegistry = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("%s %s%15s%10s %s%15s", "English:", english, transcription, "Russian:", russian, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!id.equals(word.id)) return false;
        if (!english.equals(word.english)) return false;
        if (!russian.equals(word.russian)) return false;
        if (description != null ? !description.equals(word.description) : word.description != null) return false;
        if (dateOfRegistry != null ? !dateOfRegistry.equals(word.dateOfRegistry) : word.dateOfRegistry != null)
            return false;
        return transcription != null ? transcription.equals(word.transcription) : word.transcription == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + english.hashCode();
        result = 31 * result + russian.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateOfRegistry != null ? dateOfRegistry.hashCode() : 0);
        result = 31 * result + (transcription != null ? transcription.hashCode() : 0);
        return result;
    }
}
