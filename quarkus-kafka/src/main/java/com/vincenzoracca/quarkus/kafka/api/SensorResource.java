package com.vincenzoracca.quarkus.kafka.api;

import com.vincenzoracca.quarkus.kafka.model.SensorEvent;
import com.vincenzoracca.quarkus.kafka.queue.producer.SensorProducer;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

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
