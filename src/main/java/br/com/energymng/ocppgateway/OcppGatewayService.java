package br.com.energymng.ocppgateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OcppGatewayService {

    private final OcppMessageRepository repository;
    private final ApplicationEventPublisher events;

    public OcppMessage handleInbound(String stationSerial, OcppAction action, String payload) {
        log.info("OCPP inbound [{}] from station {}", action, stationSerial);
        OcppMessage message = OcppMessage.builder()
            .messageId(UUID.randomUUID().toString())
            .stationSerialNumber(stationSerial)
            .action(action)
            .direction(OcppDirection.INBOUND)
            .payload(payload)
            .build();
        OcppMessage saved = repository.save(message);
        events.publishEvent(new OcppMessageReceivedEvent(saved.getId(), stationSerial, action, payload));
        return saved;
    }

    @Transactional(readOnly = true)
    public List<OcppMessage> findByStation(String serialNumber) {
        return repository.findByStationSerialNumberOrderByReceivedAtDesc(serialNumber);
    }
}
