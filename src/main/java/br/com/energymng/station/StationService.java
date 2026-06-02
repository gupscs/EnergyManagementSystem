package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationService {

    private final StationRepository stationRepository;

    public Station register(Station station) { return stationRepository.save(station); }

    @Transactional(readOnly = true)
    public List<Station> findAll() { return stationRepository.findAll(); }

    @Transactional(readOnly = true)
    public Station findBySerialNumber(String serialNumber) {
        return stationRepository.findBySerialNumber(serialNumber)
            .orElseThrow(() -> new IllegalArgumentException("Station not found: " + serialNumber));
    }
}
