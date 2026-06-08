package br.com.energymng.charge;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ChargeTransactionEventListener {

    private final ChargeTransactionService chargeTransactionService;

    @ApplicationModuleListener
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    void onChargeTransactionStartByCarPlugged(ChargeTransactionStartByCarPluggedEvent event) {
        chargeTransactionService.startChargeTransaction(event);
    }
}