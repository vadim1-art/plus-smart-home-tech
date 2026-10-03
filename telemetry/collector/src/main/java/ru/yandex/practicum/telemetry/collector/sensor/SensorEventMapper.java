package ru.yandex.practicum.telemetry.collector.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.sensor.model.*;

import java.time.Instant;

@Component
public class SensorEventMapper {

    public SensorEventAvro toAvro(SensorEvent event) {
        long timestampMs = event.getTimestamp().toEpochMilli();

        Object payload = switch (event) {
            case ClimateSensorEvent e -> ClimateSensorAvro.newBuilder()
                    .setTemperatureC(e.getTemperatureC())
                    .setHumidity(e.getHumidity())
                    .setCo2Level(e.getCo2Level())
                    .build();

            case LightSensorEvent e -> LightSensorAvro.newBuilder()
                    .setLinkQuality(e.getLinkQuality())
                    .setLuminosity(e.getLuminosity())
                    .build();

            case MotionSensorEvent e -> MotionSensorAvro.newBuilder()
                    .setLinkQuality(e.getLinkQuality())
                    .setMotion(e.isMotion())
                    .setVoltage(e.getVoltage())
                    .build();

            case SwitchSensorEvent e -> SwitchSensorAvro.newBuilder()
                    .setState(e.isState())
                    .build();

            case TemperatureSensorEvent e -> TemperatureSensorAvro.newBuilder()
                    .setId(e.getId())
                    .setHubId(e.getHubId())
                    .setTimestamp(Instant.ofEpochSecond(timestampMs))
                    .setTemperatureC(e.getTemperatureC())
                    .setTemperatureF(e.getTemperatureF())
                    .build();

            default -> throw new IllegalArgumentException("Неизвестный тип события датчика: " + event.getClass());
        };

        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(timestampMs))
                .setPayload(payload)
                .build();
    }
}