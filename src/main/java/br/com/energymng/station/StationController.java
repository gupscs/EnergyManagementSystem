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
class StationController {

    private final StationRepository stationRepository;
    private final PumpRepository pumpRepository;
    private final StationService stationService;
    private final PumpService pumpService;

    // -------------------------------------------------------------------------
    // Station CRUD
    // -------------------------------------------------------------------------

    @GetMapping
    ResponseEntity<List<Station>> findAllStations() {
        return ResponseEntity.ok(stationRepository.findAllByDeletedFalse());
    }

    @GetMapping("/{id}")
    ResponseEntity<Station> findStationById(@PathVariable Long id) {
        Station station = stationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        return ResponseEntity.ok(station);
    }

    @PostMapping
    ResponseEntity<Station> createStation(@Valid @RequestBody Station station) {
        station.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(stationRepository.save(station));
    }

    @PutMapping("/{id}")
    ResponseEntity<Station> updateStation(@PathVariable Long id,
                                          @Valid @RequestBody Station body) {
        return ResponseEntity.ok(stationService.update(id, body));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // Pump CRUD  (nested under /api/stations/{stationId}/pump/...)
    // -------------------------------------------------------------------------

    @GetMapping("/{stationId}/pump")
    ResponseEntity<List<Pump>> findAllPumps(@PathVariable Long stationId) {
        stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        return ResponseEntity.ok(pumpRepository.findAllByStationIdAndDeletedFalse(stationId));
    }

    @GetMapping("/{stationId}/pump/{pumpId}")
    ResponseEntity<Pump> findPumpById(@PathVariable Long stationId, @PathVariable Long pumpId) {
        stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        Pump pump = pumpRepository.findByIdAndStationIdAndDeletedFalse(pumpId, stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found"));
        return ResponseEntity.ok(pump);
    }

    @PostMapping("/{stationId}/pump")
    ResponseEntity<Pump> createPump(@PathVariable Long stationId,
                                    @Valid @RequestBody Pump pump) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pumpService.create(stationId, pump));
    }

    @PutMapping("/{stationId}/pump/{pumpId}")
    ResponseEntity<Pump> updatePump(@PathVariable Long stationId,
                                    @PathVariable Long pumpId,
                                    @Valid @RequestBody Pump body) {
        return ResponseEntity.ok(pumpService.update(stationId, pumpId, body));
    }

    @DeleteMapping("/{stationId}/pump/{pumpId}")
    ResponseEntity<Void> deletePump(@PathVariable Long stationId,
                                    @PathVariable Long pumpId) {
        pumpService.delete(stationId, pumpId);
        return ResponseEntity.noContent().build();
    }
}
