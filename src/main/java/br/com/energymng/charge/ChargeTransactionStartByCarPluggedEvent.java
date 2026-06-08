package br.com.energymng.charge;

import br.com.energymng.carmng.Car;
import br.com.energymng.carmng.CarOwner;
import br.com.energymng.station.CarPluggedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ChargeTransactionStartByCarPluggedEvent(
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
        Long pumpId,
        String pumpUniqueId,
        String pumpName,
        Long stationId,
        String stationName,
        String stationAddress,
        String stationZipcode,
        BigDecimal stationLongitude,
        BigDecimal stationLatitude
) {
    public static ChargeTransactionStartByCarPluggedEvent fromEntityAndEvent(CarOwner owner, Car car, CarPluggedEvent event) {
        return new ChargeTransactionStartByCarPluggedEvent(
                owner.getId(),
                owner.getName(),
                owner.getPhone(),
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
                event.stationId(),
                event.stationName(),
                event.stationAddress(),
                event.stationZipcode(),
                event.stationLongitude(),
                event.stationLatitude()
        );
    }

    public static ChargeTransactionStartByCarPluggedEvent from(CarPluggedEvent event) {
        return new ChargeTransactionStartByCarPluggedEvent(
                null, null, null, null,
                null, event.carPluggedUniqueId(), null, null,
                event.pluggedAt(), event.batteryLevel(),
                event.id(), event.pumpUniqueId(), event.name(),
                event.stationId(), event.stationName(), event.stationAddress(),
                event.stationZipcode(), event.stationLongitude(), event.stationLatitude()
        );
    }

    public PaymentCalculateAmountEvent toPaymentCalculateAmountEvent() {
        return new PaymentCalculateAmountEvent(
                this.carOwnerId(),
                this.carOwnerName(),
                this.carOwnerPhone(),
                this.carOwnerEmail(),
                this.carId(),
                this.carUniqueId(),
                this.carPlate(),
                this.carModel(),
                this.carPluggedAt(),
                this.batteryLevel(),
                this.pumpId()
        );
    }

    public CarOwnerStartRequestEvent toCarOwnerStartRequestEvent() {
        return new CarOwnerStartRequestEvent(
                this.carOwnerId(),
                this.carOwnerName(),
                this.carOwnerPhone(),
                this.carOwnerEmail(),
                this.carId(),
                this.carUniqueId(),
                this.carPlate(),
                this.carModel(),
                this.carPluggedAt(),
                this.batteryLevel(),
                this.pumpId()
        );
    }
}