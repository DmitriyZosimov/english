package com.myenglish.dao;

import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.Word;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("wordDaoHibernate")
public class WordDaoHibernate implements WordDao, InitializingBean {

    private SessionFactory sessionFactory;
    private LoggerProducer logger;

    public WordDaoHibernate(SessionFactory sessionFactory, LoggerProducer logger) {
        this.logger = logger;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Word> getFourRandomWords() {
        logger.debug("using getFourRandomWords...", WordDaoHibernate.class);
        return sessionFactory.getCurrentSession().createQuery("from Word w ORDER BY RAND()").setMaxResults(4).getResultList();
    }

    @Override
    public List<Word> getFourRandomWordsByDateFrom(LocalDate date) {
        logger.debug("using getFourRandomWordsByDateFrom with date:" + date, WordDaoHibernate.class);
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Word w WHERE w.dateOfRegistry>=:date ORDER BY RAND()")
                .setParameter("date", date).setMaxResults(4).getResultList();
    }

    @Override
    public Optional<Word> getRandomWord() {
        logger.debug("using getRandomWord...", WordDaoHibernate.class);
        return sessionFactory.getCurrentSession().createQuery("FROM Word w ORDER BY RAND()").setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Optional<Word> getRandomWordByDateFrom(LocalDate date) {
        logger.debug("using getRandomWordByDateFrom with date:" + date, WordDaoHibernate.class);
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Word w WHERE w.dateOfRegistry>=:date ORDER BY RAND()")
                .setParameter("date", date).setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Optional<Word> getWordByEnglish(String english) {
        logger.debug("using getWordByEnglish with english:" + english, WordDaoHibernate.class);
        return sessionFactory.getCurrentSession().createQuery("FROM Word w WHERE w.english=:english")
                .setParameter("english", english).setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Word saveOrUpdateWord(Word word) {
        logger.debug("using saveOrUpdateWord with word:" + word, WordDaoHibernate.class);
        sessionFactory.getCurrentSession().saveOrUpdate(word);
        return word;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionFactory == null) {
            throw new BeanCreationException("SessionFactory must not be null");
        }
    }
}
