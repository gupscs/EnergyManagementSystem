package br.com.energymng.station;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findBySerialNumber(String serialNumber);
}
