package com.myenglish.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class AppenderMockitoExtension {

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() {
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
