package com.myenglish.web;

import com.myenglish.web.config.WebConfig;
import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;

import java.util.Collection;
import java.util.Collections;

public class EngWebAppInitializer extends VaadinMVCWebAppInitializer {
    @Override
    protected Collection<Class<?>> getConfigurationClasses() {
        return Collections.singletonList(WebConfig.class);
    }

}
