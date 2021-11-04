package com.myenglish.web;

import com.myenglish.web.config.WebConfig;
import com.myenglish.web.config.WebLocalConfig;
import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;

import java.util.Arrays;
import java.util.Collection;

public class EngWebAppInitializer extends VaadinMVCWebAppInitializer {
    @Override
    protected Collection<Class<?>> getConfigurationClasses() {
        return Arrays.asList(WebConfig.class, WebLocalConfig.class);
    }

}
