package com.myenglish.web.vaadin.ui;

import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.spring.SpringVaadinServletService;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class VaadinSpringServlet extends VaadinServlet {

    private AnnotationConfigWebApplicationContext context;

    public VaadinSpringServlet() {
        context = new AnnotationConfigWebApplicationContext();
    }

    /**
     * Creates a spring vaadin servlet service.
     *
     * @param deploymentConfiguration
     *            the deployment configuration to be used
     *
     * @return the created spring vaadin servlet service
     *
     * @throws com.vaadin.flow.server.ServiceException
     *             if creating the vaadin servlet service fails
     */
    @Override
    protected VaadinServletService createServletService(
            DeploymentConfiguration deploymentConfiguration)
            throws ServiceException {
        SpringVaadinServletService service = new SpringVaadinServletService(
                this, deploymentConfiguration, context);
        initializeServletContext();
        service.init();
        return service;
    }

    private void initializeServletContext() {
        String contextLocation = getInitParameter("contextConfigLocation");
        context.setConfigLocation(contextLocation);
        context.setServletContext(getServletContext());
        context.refresh();
    }
}
