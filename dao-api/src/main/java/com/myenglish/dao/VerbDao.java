package com.myenglish.dao;

import com.myenglish.model.Verb;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Using for manipulating {@link Verb} in database
 */
public interface VerbDao {

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
}
