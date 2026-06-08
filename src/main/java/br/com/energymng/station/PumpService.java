package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
class PumpService {

    private final PumpRepository pumpRepository;
    private final StationRepository stationRepository;

    Pump create(Long stationId, Pump pump) {
        Station station = stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
        if (pumpRepository.existsByPumpUniqueIdAndDeletedFalse(pump.getPumpUniqueId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pump unique ID already in use");
        }
        pump.setId(null);
        pump.setStation(station);
        return pumpRepository.save(pump);
    }

    Pump update(Long pumpId, Pump body) {
        Pump pump = pumpRepository.findByIdAndDeletedFalse(pumpId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found"));
        if (!pump.getPumpUniqueId().equals(body.getPumpUniqueId())
                && pumpRepository.existsByPumpUniqueIdAndDeletedFalse(body.getPumpUniqueId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pump unique ID already in use");
        }
        pump.setPumpUniqueId(body.getPumpUniqueId());
        pump.setName(body.getName());
        pump.setUpdatedBy(body.getUpdatedBy());
        return pumpRepository.save(pump);
    }

    void delete( Long pumpId) {
        Pump pump = pumpRepository.findByIdAndDeletedFalse(pumpId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found"));
        pump.setDeleted(true);
        pumpRepository.save(pump);
    }
}