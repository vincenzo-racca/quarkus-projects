package com.vincenzoracca.quarkus.kafka.queue.consumer;

import com.vincenzoracca.quarkus.kafka.model.SensorEvent;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

/**
 * The consumer simulates an error every 3 sending messages.
 * It tries to reprocess the failed message with 2 attempts and then it sends the message in DLQ.
 */
@ApplicationScoped
public class SensorConsumer {

    private static final Logger log = LoggerFactory.getLogger(SensorConsumer.class);

    private int countMessage = 0;
    private final int MAX_COUNT = 2;


    @Incoming("sensors-in")
    @Retry(maxRetries = 2, delay = 5000L)
    public void printSensorEvent(SensorEvent message) {
        log.info("Received message: {}", message);
        if(countMessage < MAX_COUNT) {
            log.info("OK MESSAGE, countMessage=" + countMessage);
            countMessage++;
        }
        else {
            throw new RuntimeException("Message failed, countMessage=" + countMessage);
        }
    }

    @Incoming("sensor_topic_DLQ")
    public CompletionStage<Void> printSensorEventDLQ(Message<SensorEvent> message) {
        IncomingKafkaRecordMetadata<String, String> metadata = message
                .getMetadata(IncomingKafkaRecordMetadata.class)
                .orElse(null);
        String reason = new String(metadata.getHeaders().lastHeader("dead-letter-reason").value());
        log.info("Received message in DLQ: {}, reason: {}", message.getPayload(), reason);
        countMessage = 0;
        return message.ack();
    }
}
