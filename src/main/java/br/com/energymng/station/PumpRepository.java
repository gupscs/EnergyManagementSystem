package br.com.energymng.station;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface PumpRepository extends JpaRepository<Pump, Long> {

    List<Pump> findAllByDeletedFalse();

    List<Pump> findAllByStationIdAndDeletedFalse(Long stationId);

    Optional<Pump> findByIdAndDeletedFalse(Long id);

    Optional<Pump> findByIdAndStationIdAndDeletedFalse(Long id, Long stationId);

    boolean existsByPumpUniqueIdAndDeletedFalse(String pumpUniqueId);
}