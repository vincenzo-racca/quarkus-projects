package com.vincenzoracca.api;

import com.vincenzoracca.model.SensorEvent;
import com.vincenzoracca.queue.producer.SensorProducer;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.concurrent.CompletionStage;

@Path("/sensors")
public class SensorResource {

    private final SensorProducer sensorProducer;

    SensorResource(SensorProducer sensorProducer) {
        this.sensorProducer = sensorProducer;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<Void>> sendData(SensorEvent sensorEvent) {
        sensorProducer.sendMessage(sensorEvent);
        return Uni.createFrom().item(RestResponse.accepted());
    }
}
