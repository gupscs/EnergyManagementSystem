package br.com.energymng.ocppgateway;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface OcppMessageRepository extends JpaRepository<OcppMessage, Long> {
    List<OcppMessage> findByStationSerialNumberOrderByReceivedAtDesc(String serialNumber);
}
