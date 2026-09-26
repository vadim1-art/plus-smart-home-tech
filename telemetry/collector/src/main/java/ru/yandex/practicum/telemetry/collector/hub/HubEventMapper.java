package ru.yandex.practicum.telemetry.collector.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.hub.model.*;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class HubEventMapper {

    public HubEventAvro toAvro(HubEvent event) {
        long timestampMs = event.getTimestamp().toEpochMilli();

        Object payload = switch (event) {
            case DeviceAddedEvent e -> DeviceAddedEventAvro.newBuilder()
                    .setId(e.getId())
                    .setType(DeviceTypeAvro.valueOf(e.getDeviceType().name()))
                    .build();

            case DeviceRemovedEvent e -> DeviceRemovedEventAvro.newBuilder()
                    .setId(e.getId())
                    .build();

            case ScenarioAddedEvent e -> ScenarioAddedEventAvro.newBuilder()
                    .setName(e.getName())
                    .setConditions(e.getConditions().stream().map(c -> ScenarioConditionAvro.newBuilder()
                            .setSensorId(c.getSensorId())
                            .setType(ConditionTypeAvro.valueOf(c.getType().name()))
                            .setOperation(ConditionOperationAvro.valueOf(c.getOperation().name()))
                            .setValue(c.getValue())
                            .build()).collect(Collectors.toList()))
                    .setActions(e.getActions().stream().map(a -> DeviceActionAvro.newBuilder()
                            .setSensorId(a.getSensorId())
                            .setType(ActionTypeAvro.valueOf(a.getType().name()))
                            .setValue(a.getValue())
                            .build()).collect(Collectors.toList()))
                    .build();

            case ScenarioRemovedEvent e -> ScenarioRemovedEventAvro.newBuilder()
                    .setName(e.getName())
                    .build();

            default -> throw new IllegalArgumentException("Неизвестный тип события хаба: " + event.getClass());
        };

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(timestampMs))
                .setPayload(payload)
                .build();
    }
}