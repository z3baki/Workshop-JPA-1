package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * This class launches the web application in an embedded Jetty container. This
 * is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";

        // El puerto puede ser establecido por una variable de entorno 
        // Buscar la variable en dado caso que el puerto no se encuentre
        // Definir puerto para ejecutar el Jetty Server
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        PersistenceManager.getInstance().getEntityManagerFactory();

        root.setParentLoaderPriority(true);
        
        server.setHandler(root);
        
        
        server.start();
        server.join();

    }

}