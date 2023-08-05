package com.vincenzoracca.queue.producer;

import com.vincenzoracca.model.SensorEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SensorProducer {

    private static final Logger log = LoggerFactory.getLogger(SensorProducer.class);

    private final Emitter<SensorEvent> sensorEventEmitter;


    SensorProducer(@Channel("sensors-out") Emitter<SensorEvent> sensorEventEmitter) {
        this.sensorEventEmitter = sensorEventEmitter;
    }


    public void sendMessage(SensorEvent sensorEvent) {
        log.info("Sending message: {}", sensorEvent);
        sensorEventEmitter.send(Message.of(sensorEvent));
    }
}
