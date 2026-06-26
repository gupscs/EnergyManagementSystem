package br.com.energymng.common.event.payment;

import java.time.LocalDateTime;

public record PaymentCalculateAmountEvent(
        Long carOwnerId,
        String carOwnerName,
        String carOwnerPhone,
        String carOwnerIdentification,
        String carOwnerEmail,
        Long carId,
        String carUniqueId,
        String carPlate,
        String carModel,
        LocalDateTime carPluggedAt,
        Double batteryLevel,
        Long pumpId,
        Double pumpKwh
) {}
