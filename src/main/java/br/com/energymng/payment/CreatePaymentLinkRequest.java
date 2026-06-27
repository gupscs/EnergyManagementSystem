package br.com.energymng.payment;

import java.math.BigDecimal;

record CreatePaymentLinkRequest(
        Long chargeTransactionId,
        String carOwnerPhone,
        String carOwnerIdentification,
        Long pumpId,
        BigDecimal amountConfirmed,
        PaymentMethod paymentMethod
) {}