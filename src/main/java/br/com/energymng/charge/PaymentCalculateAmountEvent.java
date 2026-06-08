package br.com.energymng.charge;

import java.time.LocalDateTime;

public record PaymentCalculateAmountEvent(
        Long carOwnerId,
        String carOwnerName,
        String carOwnerPhone,
        String carOwnerEmail,
        Long carId,
        String carUniqueId,
        String carPlate,
        String carModel,
        LocalDateTime carPluggedAt,
        Double batteryLevel,
        Long pumpId
) {}