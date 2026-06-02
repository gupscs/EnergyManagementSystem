package br.com.energymng.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

interface TelemetryRepository extends JpaRepository<TelemetryRecord, Long> {
    List<TelemetryRecord> findByStationIdOrderByRecordedAtDesc(Long stationId);
    List<TelemetryRecord> findByStationIdAndRecordedAtBetween(Long stationId, LocalDateTime from, LocalDateTime to);
}
