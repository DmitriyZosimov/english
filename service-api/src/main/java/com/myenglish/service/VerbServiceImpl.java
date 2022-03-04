package com.myenglish.service;

import com.myenglish.dao.VerbDao;
import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.Verb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
public class VerbServiceImpl implements VerbService{

    private VerbDao verbDao;
    private LoggerProducer logger;
    private static final String filePath = System.getProperty("user.home") + "/.english/savedVerbs.txt";

    public VerbServiceImpl(VerbDao verbDao, LoggerProducer logger) {
        this.verbDao = verbDao;
        this.logger = logger;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Verb> getRandomVerb() {
        logger.debug("using getRandomVerb", VerbServiceImpl.class);
        return verbDao.getRandomVerb();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Verb> getRandomVerbByDateFrom(LocalDate date) {
        logger.debug("using getRandomVerbByDateFrom with date:" + date, VerbServiceImpl.class);
        return verbDao.getRandomVerbByDateFrom(date);
    }

    @Override
    public Verb saveOrUpdateVerb(Verb verb) {
        logger.debug("using saveOrUpdateVerb with verb:" + verb, VerbServiceImpl.class);
        Verb savedVerb = verbDao.saveOrUpdateVerb(verb);
        writeSavedVerbToTheFile(savedVerb, null);
        return savedVerb;
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void writeSavedVerbToTheFile(Verb verb, File file) {
        logger.debug("using writeSavedVerbToTheFile with verb:" + verb + " and file:" + file, VerbServiceImpl.class);
        if(file == null) {
            file = new File(filePath);
        }
        String resultString = String.format("('%s', '%s', '%s', '%s', '%s', '%s'),%n",
                verb.getRussian(), verb.getFirstForm(), verb.getSecondForm(), verb.getThirdForm(),
                verb.getDescription(), verb.getDateOfRegistry().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            printWriter.print(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
