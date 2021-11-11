package com.myenglish.dao;

import com.myenglish.model.Verb;

import java.time.LocalDate;
import java.util.Optional;

public interface VerbDao {

    Optional<Verb> getRandomVerb();
    Optional<Verb> getRandomVerbByDateFrom(LocalDate date);
    Verb saveOrUpdateVerb(Verb verb);
}
