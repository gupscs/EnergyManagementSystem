package br.com.energymng.common.event.ocppgateway;

import java.math.BigDecimal;

public record PumpLoadStartEvent(
        Long pumpId,
        String pumpUniqueId,
        Long chargeTransactionId,
        BigDecimal balanceInKwh
) {
}