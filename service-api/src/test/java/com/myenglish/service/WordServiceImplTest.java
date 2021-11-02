package com.myenglish.service;

import com.myenglish.dao.config.WordDaoHibernateConfig;
import com.myenglish.service.config.WordServiceConfig;
import com.myenglish.testdb.TestH2DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WordServiceConfig.class, TestH2DB.class, WordDaoHibernateConfig.class})
@Transactional
public class WordServiceImplTest {

    @Autowired
    WordService wordService;

    @Test
    public void testSetupApp() {
        Assertions.assertNotNull(wordService);
    }

}
