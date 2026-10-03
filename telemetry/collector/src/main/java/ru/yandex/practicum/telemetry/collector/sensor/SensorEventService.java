package ru.yandex.practicum.telemetry.collector.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.kafka.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.sensor.model.SensorEvent;

@Service
@RequiredArgsConstructor
public class SensorEventService {

    private static final String TOPIC = "telemetry.sensors.v1";

    private final SensorEventMapper mapper;
    private final KafkaEventProducer producer;

    public void processEvent(SensorEvent event) {
        SensorEventAvro avroEvent = mapper.toAvro(event);
        producer.send(TOPIC, event.getHubId(), event.getTimestamp().toEpochMilli(), avroEvent);
    }
}