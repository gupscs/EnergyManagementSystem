package br.com.energymng.payment;

import java.math.BigDecimal;

public record SuccessPaymentRequest(
        BigDecimal amount,
        Long chargeTransactionId,
        String gatewayTransactionId,
        PaymentMethod paymentMethod,
        String remark
) {}