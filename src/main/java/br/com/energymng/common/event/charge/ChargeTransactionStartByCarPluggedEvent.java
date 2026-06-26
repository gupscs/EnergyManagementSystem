package br.com.energymng.common.event.charge;

import br.com.energymng.carmng.Car;
import br.com.energymng.carmng.CarOwner;
import br.com.energymng.common.event.payment.PaymentCalculateAmountEvent;
import br.com.energymng.common.event.station.CarPluggedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ChargeTransactionStartByCarPluggedEvent(
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
        String pumpUniqueId,
        String pumpName,
        Integer pumpCode,
        Double pumpKwh,
        Long stationId,
        String stationName,
        String stationAddress,
        String stationZipcode,
        BigDecimal stationLongitude,
        BigDecimal stationLatitude,
        Integer stationCode
) {
    public static ChargeTransactionStartByCarPluggedEvent fromEntityAndEvent(CarOwner owner, Car car, CarPluggedEvent event) {
        return new ChargeTransactionStartByCarPluggedEvent(
                owner.getId(),
                owner.getName(),
                owner.getPhone(),
                owner.getIdentification(),
                owner.getEmail(),
                car.getId(),
                car.getCarUniqueId(),
                car.getPlate(),
                car.getModel(),
                event.pluggedAt(),
                event.batteryLevel(),
                event.id(),
                event.pumpUniqueId(),
                event.name(),
                event.pumpCode(),
                event.pumpKwh(),
                event.stationId(),
                event.stationName(),
                event.stationAddress(),
                event.stationZipcode(),
                event.stationLongitude(),
                event.stationLatitude(),
                event.stationCode()
        );
    }

    public static ChargeTransactionStartByCarPluggedEvent from(CarPluggedEvent event) {
        return new ChargeTransactionStartByCarPluggedEvent(
                null, null, null, null, null,
                null, event.carPluggedUniqueId(), null, null,
                event.pluggedAt(), event.batteryLevel(),
                event.id(), event.pumpUniqueId(), event.name(), event.pumpCode(),
                event.pumpKwh(),
                event.stationId(), event.stationName(), event.stationAddress(),
                event.stationZipcode(), event.stationLongitude(), event.stationLatitude(),
                event.stationCode()
        );
    }

    public PaymentCalculateAmountEvent toPaymentCalculateAmountEvent() {
        return new PaymentCalculateAmountEvent(
                this.carOwnerId(),
                this.carOwnerName(),
                this.carOwnerPhone(),
                this.carOwnerIdentification(),
                this.carOwnerEmail(),
                this.carId(),
                this.carUniqueId(),
                this.carPlate(),
                this.carModel(),
                this.carPluggedAt(),
                this.batteryLevel(),
                this.pumpId(),
                this.pumpKwh()
        );
    }
}
