package com.myenglish.service;

import com.myenglish.model.Verb;
import org.springframework.lang.Nullable;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

public interface VerbService {

    Optional<Verb> getRandomVerb();
    Optional<Verb> getRandomVerbByDateFrom(LocalDate date);
    Verb saveOrUpdateVerb(Verb verb);
    void writeSavedVerbToTheFile(Verb verb, @Nullable File file);
}
