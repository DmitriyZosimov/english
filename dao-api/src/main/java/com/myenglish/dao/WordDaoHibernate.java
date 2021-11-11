package com.myenglish.dao;

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

    public WordDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Word> getFourRandomWords() {
        return sessionFactory.getCurrentSession().createQuery("from Word w ORDER BY RAND()").setMaxResults(4).getResultList();
    }

    @Override
    public List<Word> getFourRandomWordsByDateFrom(LocalDate date) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Word w WHERE w.dateOfRegistry>=:date ORDER BY RAND()")
                .setParameter("date", date).setMaxResults(4).getResultList();
    }

    @Override
    public Optional<Word> getRandomWord() {
        return sessionFactory.getCurrentSession().createQuery("FROM Word w ORDER BY RAND()").setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Optional<Word> getRandomWordByDateFrom(LocalDate date) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Word w WHERE w.dateOfRegistry>=:date ORDER BY RAND()")
                .setParameter("date", date).setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Optional<Word> getWordByEnglish(String english) {
        return sessionFactory.getCurrentSession().createQuery("FROM Word w WHERE w.english=:english")
                .setParameter("english", english).setMaxResults(1).uniqueResultOptional();
    }

    @Override
    public Word saveOrUpdateWord(Word word) {
        sessionFactory.getCurrentSession().saveOrUpdate(word);
        return word;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(sessionFactory == null) {
            throw new BeanCreationException("SessionFactory must not be null");
        }
    }
}
