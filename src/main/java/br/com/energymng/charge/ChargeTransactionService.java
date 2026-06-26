package br.com.energymng.charge;

import br.com.energymng.common.event.charge.ChargeTransactionStartByCarPluggedEvent;
import br.com.energymng.common.event.notification.CarOwnerNotificationEvent;
import br.com.energymng.notification.NotificationMessage;
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

    public void startChargeTransaction(ChargeStartRequest chargeStartRequest) {
        Optional<ChargeTransaction> existing = repository.findTopByPumpIdAndChargeStatusInOrderByCreatedAtDesc(
                chargeStartRequest.pumpId(), List.of(ChargeStatus.CREATED, ChargeStatus.STARTED));

        if (existing.isPresent()) {
            existing.get().populateEmptyFields(chargeStartRequest);
            updateExisting(existing.get());
        } else {
            ChargeTransaction tx = new ChargeTransaction();
            tx.populateEmptyFields(chargeStartRequest);
            createNew(tx);
            if (chargeStartRequest.carOwnerPhone() != null && !chargeStartRequest.carOwnerPhone().isBlank()) {
                eventPublisher.publishEvent(new CarOwnerNotificationEvent(
                        chargeStartRequest.carOwnerPhone(),
                        chargeStartRequest.carOwnerIdentification(),
                        NotificationMessage.CAR_PLUG_REQUEST));
            }
        }
    }

    void startChargeTransaction(ChargeTransactionStartByCarPluggedEvent event) {
        Optional<ChargeTransaction> existing = repository.findTopByPumpIdAndChargeStatusInOrderByCreatedAtDesc(
                event.pumpId(), List.of(ChargeStatus.CREATED, ChargeStatus.STARTED));

        if (existing.isPresent()) {
            existing.get().populateEmptyFields(event);
            updateExisting(existing.get());
        } else {
            ChargeTransaction tx = new ChargeTransaction();
            tx.populateEmptyFields(event);
            createNew(tx);
        }
    }

    private void updateExisting(ChargeTransaction tx) {
        tx.setChargeStatus(ChargeStatus.STARTED);
        repository.save(tx);
        log.info("ChargeTransaction updated to STARTED id={} pumpId={}", tx.getId(), tx.getPumpId());

        try {
            eventPublisher.publishEvent(tx.toPaymentCalculateAmountEvent());
        } catch (Exception e) {
            log.error("Failed to publish PaymentCalculateAmountEvent chargeTransactionId={}", tx.getId(), e);
            throw e;
        }
    }

    private void createNew(ChargeTransaction tx) {

        tx.setChargeStatus(ChargeStatus.CREATED);
        repository.save(tx);
        log.info("ChargeTransaction created id={} pumpId={}", tx.getId(), tx.getPumpId());

        if (tx.getCarOwnerPhone() != null && !tx.getCarOwnerPhone().isBlank()) {
            try {
                tx.setChargeStatus(ChargeStatus.STARTED);
                repository.save(tx);
                eventPublisher.publishEvent(tx.toPaymentCalculateAmountEvent());
            } catch (Exception e) {
                log.error("Failed to publish CarOwnerStartRequestEvent chargeTransactionId={}", tx.getId(), e);
                throw e;
            }
        }
    }


}