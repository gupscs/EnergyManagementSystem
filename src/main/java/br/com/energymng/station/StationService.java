package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
class StationService {

    private final StationRepository stationRepository;

    Station update(Long id, Station body) {
        Station station = stationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        station.setName(body.getName());
        station.setAddress(body.getAddress());
        station.setZipcode(body.getZipcode());
        station.setLongitude(body.getLongitude());
        station.setLatitude(body.getLatitude());
        station.setUpdatedBy(body.getUpdatedBy());
        return stationRepository.save(station);
    }

    void delete(Long id) {
        Station station = stationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        station.setDeleted(true);
        stationRepository.save(station);
    }
}