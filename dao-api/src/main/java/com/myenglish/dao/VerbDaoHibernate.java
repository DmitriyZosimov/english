package com.myenglish.dao;

import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.Verb;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("verbDaoHibernate")
public class VerbDaoHibernate implements VerbDao, InitializingBean {

    private SessionFactory sessionFactory;
    private LoggerProducer logger;

    public VerbDaoHibernate(SessionFactory sessionFactory, LoggerProducer logger) {
        this.sessionFactory = sessionFactory;
        this.logger = logger;
    }

    @Override
    public Optional<Verb> getRandomVerb() {
        logger.debug("using getRandomVerb...", VerbDaoHibernate.class);
        return sessionFactory.getCurrentSession().createQuery("FROM Verb v ORDER BY RAND()").setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Optional<Verb> getRandomVerbByDateFrom(LocalDate date) {
        logger.debug("using getRandomVerbByDate with date:" + date, VerbDaoHibernate.class);
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Verb v WHERE v.dateOfRegistry>=:date ORDER BY RAND()")
                .setParameter("date", date).setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Verb saveOrUpdateVerb(Verb verb) {
        logger.debug("using saveOrUpdateVerb with verb:" + verb, VerbDaoHibernate.class);
        sessionFactory.getCurrentSession().saveOrUpdate(verb);
        return verb;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionFactory == null) {
            throw new BeanCreationException("SessionFactory must not be null");
        }
    }
}
