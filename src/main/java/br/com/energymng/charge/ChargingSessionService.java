package br.com.energymng.charge;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargingSessionService {

    private final ChargingSessionRepository repository;
    private final ApplicationEventPublisher events;

    public ChargingSession start(Long userId, Long stationId) {
        ChargingSession session = ChargingSession.builder()
            .userId(userId)
            .stationId(stationId)
            .status(SessionStatus.ACTIVE)
            .startedAt(LocalDateTime.now())
            .build();
        return repository.save(session);
    }

    public ChargingSession stop(Long sessionId, Double energyKwh) {
        ChargingSession session = findById(sessionId);
        session.setStatus(SessionStatus.COMPLETED);
        session.setEnergyConsumedKwh(energyKwh);
        session.setEndedAt(LocalDateTime.now());
        ChargingSession saved = repository.save(session);
        events.publishEvent(new ChargingSessionCompletedEvent(saved.getId(), saved.getUserId(), energyKwh));
        return saved;
    }

    @Transactional(readOnly = true)
    public ChargingSession findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Session not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<ChargingSession> findByUser(Long userId) { return repository.findByUserId(userId); }
}
