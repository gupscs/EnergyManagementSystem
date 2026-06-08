package br.com.energymng.station;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
class PumpController {

    private final StationRepository stationRepository;
    private final PumpRepository pumpRepository;
    private final StationService stationService;
    private final PumpService pumpService;

     // -------------------------------------------------------------------------
    // Pump CRUD  (nested under /api/stations/{stationId}/pump/...)
    // -------------------------------------------------------------------------

    @GetMapping("/{stationId}/pump")
    ResponseEntity<List<Pump>> findAllPumpsByStationId(@PathVariable Long stationId) {
        stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        return ResponseEntity.ok(pumpRepository.findAllByStationIdAndDeletedFalse(stationId));
    }

    @GetMapping("/pump")
    ResponseEntity<List<Pump>> findAllPumps() {
        return ResponseEntity.ok(pumpRepository.findAllByDeletedFalse());
    }

    @GetMapping("/{stationId}/pump/{pumpId}")
    ResponseEntity<Pump> findPumpByStationIdAndPumpId(@PathVariable Long stationId, @PathVariable Long pumpId) {
        stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        Pump pump = pumpRepository.findByIdAndStationIdAndDeletedFalse(pumpId, stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found"));
        return ResponseEntity.ok(pump);
    }

    @GetMapping("/pump/{pumpId}")
    ResponseEntity<Pump> findPumpById(@PathVariable Long pumpId) {
        Pump pump = pumpRepository.findByIdAndDeletedFalse(pumpId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found"));
        return ResponseEntity.ok(pump);
    }

    @PostMapping("/{stationId}/pump")
    ResponseEntity<Pump> createPump(@PathVariable Long stationId,
                                    @Valid @RequestBody Pump pump) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pumpService.create(stationId, pump));
    }

    @PutMapping("/pump/{pumpId}")
    ResponseEntity<Pump> updatePump(@PathVariable Long pumpId,
                                    @Valid @RequestBody Pump body) {
        return ResponseEntity.ok(pumpService.update(pumpId, body));
    }

    @DeleteMapping("/pump/{pumpId}")
    ResponseEntity<Void> deletePump(@PathVariable Long pumpId) {
        pumpService.delete( pumpId);
        return ResponseEntity.noContent().build();
    }
}
