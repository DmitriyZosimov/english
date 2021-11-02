package com.myenglish.dao;

import com.english.aws.RDSConfig;
import com.myenglish.dao.config.WordDaoHibernateConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WordDaoHibernateConfig.class, RDSConfig.class})
public class WordDaoHibernateConfigWithRDSTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void setupSessionFactory() {
        Assertions.assertNotNull(sessionFactory);
    }
}
