package com.vincenzoracca.quarkus.jpa.reactive.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/custom-message")
public class CustomMessageApi {


    private final String message;

    CustomMessageApi(@ConfigProperty(name = "custom.message") String message){
        this.message = message;
    }

    @GET
    public String getMessage() {
        return message;
    }

    @GET
    @Path("without-cdi")
    public String getMessageWithoutCdi() {
        return ConfigProvider.getConfig().getValue("custom.message", String.class);
    }
}
