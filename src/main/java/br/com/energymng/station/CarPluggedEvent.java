package br.com.energymng.station;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarPluggedEvent(
        Long id,
        Long stationId,
        String stationName,
        String stationAddress,
        String stationZipcode,
        BigDecimal stationLongitude,
        BigDecimal stationLatitude,
        String pumpUniqueId,
        String name,
        PumpStatus pumpStatus,
        String carPluggedUniqueId,
        LocalDateTime pluggedAt,
        Double batteryLevel,
        boolean deleted
) {}