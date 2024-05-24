package com.nqt.mybank;

import com.nqt.mybank.context.MyBankApplicationConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {

        int serverPort = 8080;
        String portProperty = System.getProperty("server.port");
        if (portProperty != null) {
            serverPort = Integer.parseInt(portProperty);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(serverPort);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);

        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
        Wrapper servlet = tomcat.addServlet(tomcatCtx, "DispatcherServlet", dispatcherServlet);

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MyBankApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }
}
