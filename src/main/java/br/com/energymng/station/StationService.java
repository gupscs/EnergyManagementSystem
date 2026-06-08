package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class StationService {

    private final StationRepository stationRepository;

    Station update(Long id, Station body) {
        Station station = stationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Station not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found");
                });
        station.setName(body.getName());
        station.setAddress(body.getAddress());
        station.setZipcode(body.getZipcode());
        station.setLongitude(body.getLongitude());
        station.setLatitude(body.getLatitude());
        station.setUpdatedBy(body.getUpdatedBy());
        Station saved = stationRepository.save(station);
        log.info("Station updated id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    void delete(Long id) {
        Station station = stationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Station not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found");
                });
        station.setDeleted(true);
        stationRepository.save(station);
        log.info("Station deleted id={}", id);
    }
}
