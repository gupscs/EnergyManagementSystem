package br.com.energymng.charge;

import br.com.energymng.common.event.charge.ChargeTransactionStartByCarPluggedEvent;
import br.com.energymng.common.event.payment.ChargeTransactionPaidEvent;
import br.com.energymng.common.event.payment.ChargeTransactionPaiedEvent;
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

    @ApplicationModuleListener
    void onChargeTransactionPaiedEvent(ChargeTransactionPaidEvent event) {
        chargeTransactionService.chargeTransactionPaided(event);
    }
}