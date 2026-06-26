package br.com.energymng.payment;

import br.com.energymng.common.event.payment.PaymentCalculateAmountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PaymentEventListener {

    private final PaymentService paymentService;

    @ApplicationModuleListener
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    void onPaymentCalculateAmount(PaymentCalculateAmountEvent event) {
        paymentService.calculateAmount(event);
    }
}