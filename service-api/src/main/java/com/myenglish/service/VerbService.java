package com.myenglish.service;

import com.myenglish.model.Verb;
import org.springframework.lang.Nullable;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service layout is using for manipulating verbs.
 */
public interface VerbService {

    /**
     * Pull up a verb from database.
     *
     * @return {@link Optional} with obtained {@link Verb} from database.
     */
    Optional<Verb> getRandomVerb();

    /**
     * Pull up a verb from database with date constraint
     *
     * @return {@link Optional} with obtained {@link Verb} which was saved after this {@param date}
     */
    Optional<Verb> getRandomVerbByDateFrom(LocalDate date);

    /**
     * Save or update a {@param verb}
     *
     * @return return saved or updated {@link Verb}
     */
    Verb saveOrUpdateVerb(Verb verb);

    /**
     * Save a {@param verb} to the file
     */
    void writeSavedVerbToTheFile(Verb verb, @Nullable File file);
}
