package br.com.energymng.common.event.notification;

import java.math.BigDecimal;

public record CarOwnerPaymentCalculationNotificationEvent(
        String carOwnerPhone,
        String carOwnerIdentification,
        Double batteryLevel,
        boolean hasEstimative,
        BigDecimal estimatedMaxAmount,
        Integer estimatedMaxTimeInMin,
        Double estimatedMaxKwh,
        BigDecimal kwhPrice,
        BigDecimal iddlePricePerMin,
        Long pumpId
) {}