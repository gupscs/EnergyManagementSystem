package br.com.energymng.station;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class PumpService {

    private final PumpRepository pumpRepository;
    private final StationRepository stationRepository;
    private final ApplicationEventPublisher eventPublisher;

    Pump create(Long stationId, Pump pump) {
        Station station = stationRepository.findByIdAndDeletedFalse(stationId)
                .orElseThrow(() -> {
                    log.warn("Station not found stationId={}", stationId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found");
                });
        if (pumpRepository.existsByPumpUniqueIdAndDeletedFalse(pump.getPumpUniqueId())) {
            log.warn("Pump uniqueId already in use uniqueId={}", pump.getPumpUniqueId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pump unique ID already in use");
        }
        pump.setId(null);
        pump.setStation(station);
        Pump saved = pumpRepository.save(pump);
        log.info("Pump created uniqueId={} stationId={}", saved.getPumpUniqueId(), stationId);
        return saved;
    }

    Pump update(Long pumpId, Pump body) {
        Pump pump = pumpRepository.findByIdAndDeletedFalse(pumpId)
                .orElseThrow(() -> {
                    log.warn("Pump not found pumpId={}", pumpId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found");
                });
        if (!pump.getPumpUniqueId().equals(body.getPumpUniqueId())
                && pumpRepository.existsByPumpUniqueIdAndDeletedFalse(body.getPumpUniqueId())) {
            log.warn("Pump uniqueId already in use uniqueId={}", body.getPumpUniqueId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pump unique ID already in use");
        }
        pump.setPumpUniqueId(body.getPumpUniqueId());
        pump.setName(body.getName());
        pump.setUpdatedBy(body.getUpdatedBy());
        Pump saved = pumpRepository.save(pump);
        log.info("Pump updated pumpId={} uniqueId={}", pumpId, saved.getPumpUniqueId());
        return saved;
    }

    void delete(Long pumpId) {
        Pump pump = pumpRepository.findByIdAndDeletedFalse(pumpId)
                .orElseThrow(() -> {
                    log.warn("Pump not found pumpId={}", pumpId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pump not found");
                });
        pump.setDeleted(true);
        pumpRepository.save(pump);
        log.info("Pump deleted pumpId={}", pumpId);
    }

    Pump carPlugged(CarPluggedRequest carPluggedRequest) {
        Pump pump = pumpRepository.findByPumpUniqueIdAndDeletedFalse(carPluggedRequest.pumpUniqueId())
                .orElseThrow(() -> {
                    log.warn("Invalid pump uniqueId={}", carPluggedRequest.pumpUniqueId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Pump");
                });
        if (pump.getPumpStatus() != PumpStatus.AVALIABLE) {
            log.warn("Pump not available uniqueId={} status={}", pump.getPumpUniqueId(), pump.getPumpStatus());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pump is not avaliable, try later");
        }
        pump.setPumpStatus(PumpStatus.BUSY);
        pump.setCarPluggedUniqueId(carPluggedRequest.carUniqueId());
        pump.setPluggedAt(LocalDateTime.now());
        Pump saved = pumpRepository.save(pump);
        log.info("Car plugged pumpUniqueId={} carUniqueId={}", saved.getPumpUniqueId(), saved.getCarPluggedUniqueId());
        try {
            eventPublisher.publishEvent(saved.toCarPluggedEvent(carPluggedRequest.batteryLevel()));
        } catch (Exception e) {
            log.error("Failed to publish CarPluggedEvent pumpUniqueId={}", saved.getPumpUniqueId(), e);
            throw e;
        }
        return saved;
    }
}
