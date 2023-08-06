package com.vincenzoracca.quarkus.kafka.queue;

import com.vincenzoracca.quarkus.kafka.model.SensorEvent;
import com.vincenzoracca.quarkus.kafka.queue.consumer.SensorConsumer;
import com.vincenzoracca.quarkus.kafka.queue.producer.SensorProducer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
@QuarkusTestResource(KafkaResource.class)
class SensorConsumerTests {

    @Inject
    SensorProducer sensorProducer;

    @InjectSpy
    SensorConsumer sensorConsumer;


    @Test
    void testSendAndReceivedEvent() {
        var sensorEvent = new SensorEvent("id-mock", 10.5);
        sensorProducer.sendMessage(sensorEvent);

        ArgumentCaptor<SensorEvent> sensorReceived = ArgumentCaptor.forClass(SensorEvent.class);

        verify(sensorConsumer, timeout(2000L)).printSensorEvent(sensorReceived.capture());
        verify(sensorConsumer, never()).printSensorEventDLQ(any());

        sensorProducer.sendMessage(sensorEvent);
        verify(sensorConsumer, timeout(2000L)).printSensorEvent(sensorReceived.capture());
        verify(sensorConsumer, never()).printSensorEventDLQ(any());
        assertEquals(sensorEvent, sensorReceived.getValue());

    }
}
