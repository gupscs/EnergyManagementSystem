package br.com.energymng.telemetry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TelemetryService {

    private final TelemetryRepository repository;

    public TelemetryRecord record(TelemetryRecord record) {
        return repository.save(record);
    }

    @Transactional(readOnly = true)
    public List<TelemetryRecord> findByStation(Long stationId) {
        return repository.findByStationIdOrderByRecordedAtDesc(stationId);
    }

    @Transactional(readOnly = true)
    public List<TelemetryRecord> findByStationAndPeriod(Long stationId, LocalDateTime from, LocalDateTime to) {
        return repository.findByStationIdAndRecordedAtBetween(stationId, from, to);
    }
}
