package ru.yandex.practicum.telemetry.collector.hub;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.hub.model.HubEvent;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubEventController {

    private final HubEventService hubEventService;

    @PostMapping
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        hubEventService.processEvent(event);
    }
}