package com.profitsoft.internship.lesson7_8.task1;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class WebServerRunner {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        Tomcat tomcat = new Tomcat();

        setPort(tomcat);
        setContext(tomcat);

        tomcat.enableNaming();
        tomcat.getConnector();

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

        tomcat.getServer().await();
    }

    private static void setContext(Tomcat tomcat) {
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new
                File("src/main/webapp/").getAbsolutePath());

        File additionWebInfClasses = new File("target/classes");

        WebResourceRoot resources = new StandardRoot(ctx);

        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));

        ctx.setResources(resources);
    }

    private static void setPort(Tomcat tomcat) {
        String webPort = System.getenv("PORT");

        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));
    }
}
