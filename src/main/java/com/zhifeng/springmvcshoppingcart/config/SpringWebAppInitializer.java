package com.zhifeng.springmvcshoppingcart.config;
 
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
 
//equals with web.xml
//configure the springServlet, contextLoaderListener, and encodingFilter
//initialize spring web application
public class SpringWebAppInitializer implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
 
        //springDispatcher 
        //initialize a servlet
        //the class of the dispatcher
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
                new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        //urls with this pattern will be handled by dispatcher
        dispatcher.addMapping("/");
         
         
        //configure contextLoaderListener
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
 
        servletContext.addListener(contextLoaderListener);
         
         
        // Filter
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
 
        //urls encoding, i.e., how the url is encoded
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }
 
}