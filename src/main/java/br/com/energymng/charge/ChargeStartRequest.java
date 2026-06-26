package br.com.energymng.charge;

import java.math.BigDecimal;

record ChargeStartRequest(
        String carOwnerPhone,
        String carOwnerIdentification,
        Long pumpId,
        String pumpUniqueId,
        String pumpName,
        Integer pumpCode,
        Long stationId,
        String stationName,
        String stationAddress,
        String stationZipcode,
        BigDecimal stationLongitude,
        BigDecimal stationLatitude,
        Integer stationCode
) {}
