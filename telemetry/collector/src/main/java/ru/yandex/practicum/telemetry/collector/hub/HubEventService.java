package ru.yandex.practicum.telemetry.collector.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.collector.kafka.KafkaEventProducer;

@Service
@RequiredArgsConstructor
public class HubEventService {

    private static final String TOPIC = "telemetry.hubs.v1";

    private final HubEventMapper mapper;
    private final KafkaEventProducer producer;

    public void processEvent(HubEvent event) {
        HubEventAvro avroEvent = mapper.toAvro(event);
        producer.send(TOPIC, event.getHubId(), event.getTimestamp().toEpochMilli(), avroEvent);
    }
}