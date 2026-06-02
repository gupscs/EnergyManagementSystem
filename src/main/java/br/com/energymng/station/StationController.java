package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public List<Station> findAll() { return stationService.findAll(); }

    @PostMapping
    public ResponseEntity<Station> register(@RequestBody Station station) {
        return ResponseEntity.ok(stationService.register(station));
    }
}
