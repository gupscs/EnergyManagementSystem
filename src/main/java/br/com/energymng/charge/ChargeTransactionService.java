package br.com.energymng.charge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class ChargeTransactionService {

    private final ChargeTransactionRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    void startChargeTransaction(ChargeTransactionStartByCarPluggedEvent event) {
        Optional<ChargeTransaction> existing = repository.findTopByPumpIdAndChargeStatusInOrderByCreatedAtDesc(
                event.pumpId(), List.of(ChargeStatus.CREATED, ChargeStatus.STARTED));

        if (existing.isPresent()) {
            updateExisting(existing.get(), event);
        } else {
            createNew(event);
        }
    }

    private void updateExisting(ChargeTransaction tx, ChargeTransactionStartByCarPluggedEvent event) {
        tx.setByEvent(event);
        tx.setChargeStatus(ChargeStatus.STARTED);
        repository.save(tx);
        log.info("ChargeTransaction updated to STARTED id={} pumpId={}", tx.getId(), event.pumpId());

        try {
            eventPublisher.publishEvent(event.toPaymentCalculateAmountEvent());
        } catch (Exception e) {
            log.error("Failed to publish PaymentCalculateAmountEvent chargeTransactionId={}", tx.getId(), e);
            throw e;
        }
    }

    private void createNew(ChargeTransactionStartByCarPluggedEvent event) {
        ChargeTransaction tx = new ChargeTransaction();
        tx.setByEvent(event);
        tx.setChargeStatus(ChargeStatus.CREATED);
        repository.save(tx);
        log.info("ChargeTransaction created id={} pumpId={}", tx.getId(), event.pumpId());

        if (event.carOwnerPhone() != null && !event.carOwnerPhone().isBlank()) {
            try {
                tx.setChargeStatus(ChargeStatus.STARTED);
                repository.save(tx);
                eventPublisher.publishEvent(event.toPaymentCalculateAmountEvent());
            } catch (Exception e) {
                log.error("Failed to publish CarOwnerStartRequestEvent chargeTransactionId={}", tx.getId(), e);
                throw e;
            }
        }
    }

}