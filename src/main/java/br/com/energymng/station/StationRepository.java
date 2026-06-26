package br.com.energymng.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByDeletedFalse();

    Optional<Station> findByIdAndDeletedFalse(Long id);

    @Query("SELECT s FROM Station s LEFT JOIN FETCH s.pumps WHERE s.stationCode = :stationCode AND s.deleted = false")
    Optional<Station> findByStationCodeWithPumps(@Param("stationCode") Integer stationCode);
}