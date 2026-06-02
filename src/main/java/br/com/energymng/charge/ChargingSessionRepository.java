package br.com.energymng.charge;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {
    List<ChargingSession> findByUserId(Long userId);
    List<ChargingSession> findByStationId(Long stationId);
}
