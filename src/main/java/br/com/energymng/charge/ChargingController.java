package br.com.energymng.charge;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/charging")
@RequiredArgsConstructor
public class ChargingController {

    private final ChargingSessionService service;

    @PostMapping("/start")
    public ResponseEntity<ChargingSession> start(@RequestParam Long userId, @RequestParam Long stationId) {
        return ResponseEntity.ok(service.start(userId, stationId));
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<ChargingSession> stop(@PathVariable Long id, @RequestParam Double energyKwh) {
        return ResponseEntity.ok(service.stop(id, energyKwh));
    }

    @GetMapping("/user/{userId}")
    public List<ChargingSession> findByUser(@PathVariable Long userId) {
        return service.findByUser(userId);
    }
}
