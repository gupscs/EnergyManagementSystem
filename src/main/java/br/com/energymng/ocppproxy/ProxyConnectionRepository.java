package br.com.energymng.ocppproxy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

interface ProxyConnectionRepository extends JpaRepository<ProxyConnection, Long> {
    Optional<ProxyConnection> findByStationSerialNumber(String serialNumber);
    List<ProxyConnection> findByStatus(ConnectionStatus status);
}
