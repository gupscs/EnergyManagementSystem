package br.com.energymng.common.event.station;

import br.com.energymng.station.PumpStatus;

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
        Integer stationCode,
        String pumpUniqueId,
        String name,
        PumpStatus pumpStatus,
        Integer pumpCode,
        String carPluggedUniqueId,
        LocalDateTime pluggedAt,
        Double batteryLevel,
        Double pumpKwh,
        boolean deleted
) {}
