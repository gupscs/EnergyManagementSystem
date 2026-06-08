package br.com.energymng.station;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
class StationController {

    private final StationRepository stationRepository;
    private final StationService stationService;

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
        Station saved = stationRepository.save(station);
        log.info("Station created id={} name={}", saved.getId(), saved.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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
}
