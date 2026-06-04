package br.com.energymng.station;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByDeletedFalse();

    Optional<Station> findByIdAndDeletedFalse(Long id);
}