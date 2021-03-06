package com.myenglish.dao;

import com.myenglish.aws.RDSConfig;
import com.myenglish.dao.config.DaoHibernateConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoHibernateConfig.class, RDSConfig.class})
@ActiveProfiles("aws")
@Disabled("Commit this annotation after entering aws credentials")
public class WordDaoHibernateConfigWithRDSTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void setupSessionFactory() {
        Assertions.assertNotNull(sessionFactory);
    }
}
