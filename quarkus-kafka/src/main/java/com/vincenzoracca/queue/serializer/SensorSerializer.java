package com.vincenzoracca.queue.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincenzoracca.model.SensorEvent;
import org.apache.kafka.common.serialization.Serializer;

public class SensorSerializer implements Serializer<SensorEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, SensorEvent sensorEvent) {
        try {
            return objectMapper.writeValueAsBytes(sensorEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
