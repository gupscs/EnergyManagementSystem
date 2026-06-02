package br.com.energymng.telemetry;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<TelemetryRecord> record(@RequestBody TelemetryRecord record) {
        return ResponseEntity.ok(telemetryService.record(record));
    }

    @GetMapping("/station/{stationId}")
    public List<TelemetryRecord> findByStation(@PathVariable Long stationId) {
        return telemetryService.findByStation(stationId);
    }

    @GetMapping("/station/{stationId}/period")
    public List<TelemetryRecord> findByPeriod(
            @PathVariable Long stationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return telemetryService.findByStationAndPeriod(stationId, from, to);
    }
}
