package br.com.energymng.ocppproxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OcppProxyService {

    private final ProxyConnectionRepository repository;

    public ProxyConnection connect(String serialNumber, String remoteAddress, String ocppVersion) {
        log.info("Station {} connecting via proxy from {}", serialNumber, remoteAddress);
        ProxyConnection conn = repository.findByStationSerialNumber(serialNumber)
            .orElse(ProxyConnection.builder()
                .stationSerialNumber(serialNumber)
                .ocppVersion(ocppVersion)
                .build());
        conn.setRemoteAddress(remoteAddress);
        conn.setStatus(ConnectionStatus.CONNECTED);
        conn.setConnectedAt(LocalDateTime.now());
        conn.setDisconnectedAt(null);
        return repository.save(conn);
    }

    public ProxyConnection disconnect(String serialNumber) {
        ProxyConnection conn = findBySerial(serialNumber);
        conn.setStatus(ConnectionStatus.DISCONNECTED);
        conn.setDisconnectedAt(LocalDateTime.now());
        return repository.save(conn);
    }

    @Transactional(readOnly = true)
    public List<ProxyConnection> findActiveConnections() {
        return repository.findByStatus(ConnectionStatus.CONNECTED);
    }

    @Transactional(readOnly = true)
    public ProxyConnection findBySerial(String serialNumber) {
        return repository.findByStationSerialNumber(serialNumber)
            .orElseThrow(() -> new IllegalArgumentException("Connection not found: " + serialNumber));
    }
}
