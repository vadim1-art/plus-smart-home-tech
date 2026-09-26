package ru.yandex.practicum.telemetry.collector.sensor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.sensor.model.SensorEvent;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorEventController {

    private final SensorEventService sensorEventService;

    @PostMapping
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        sensorEventService.processEvent(event);
    }
}