/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api;

import br.com.estudo.agenda.api.provider.BeanValidationExceptionMapper;
import br.com.estudo.agenda.filter.CORSFilter;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.environment.se.Weld;

/**
 *
 * @author eduardo
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:3000/agenda/api/");

    public static void main(String[] args) {
        try {
            System.out.println("Jersey CDI Example App");

            final Weld weld = new Weld();
            weld.initialize();

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createJaxRsApp(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                    weld.shutdown();
                }
            }));
            server.start();

            System.out.println(String.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
                    BASE_URI, "application.wadl"));

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ResourceConfig createJaxRsApp() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("br.com.estudo.agenda.api.resource");
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(new BeanValidationExceptionMapper());
        resourceConfig.register(new CORSFilter());
        resourceConfig.property(org.glassfish.jersey.server.ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        return resourceConfig;
    }

}
