package br.com.energymng.charge;

import br.com.energymng.common.entity.AuditableEntity;
import br.com.energymng.common.event.charge.ChargeTransactionStartByCarPluggedEvent;
import br.com.energymng.common.event.payment.PaymentCalculateAmountEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "charge_transaction")
@Getter
@Setter
@NoArgsConstructor
public class ChargeTransaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_status", nullable = false, length = 30)
    private ChargeStatus chargeStatus;

    @Column(name = "charge_status_detail")
    private String chargeStatusDetail;

    // ── Car owner snapshot ──────────────────────────────────────────────────
    @Column(name = "car_owner_id")
    private Long carOwnerId;

    @Column(name = "car_owner_name")
    private String carOwnerName;

    @Column(name = "car_owner_phone")
    private String carOwnerPhone;

    @Column(name = "car_owner_identification")
    private String carOwnerIdentification;

    @Column(name = "car_owner_email")
    private String carOwnerEmail;

    // ── Car snapshot ────────────────────────────────────────────────────────
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "car_unique_id")
    private String carUniqueId;

    @Column(name = "car_plate")
    private String carPlate;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_plugged_at")
    private LocalDateTime carPluggedAt;

    @Column(name = "initial_battery_level_in_percent")
    private Double initialBatteryLevelInPercent;

    // ── Pump snapshot ───────────────────────────────────────────────────────
    @Column(name = "pump_id")
    private Long pumpId;

    @Column(name = "pump_unique_id")
    private String pumpUniqueId;

    @Column(name = "pump_name")
    private String pumpName;

    @Column(name = "pump_code")
    private Integer pumpCode;

    @Column(name = "pump_kwh")
    private Double pumpKwh;

    // ── Station snapshot ────────────────────────────────────────────────────
    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "station_address")
    private String stationAddress;

    @Column(name = "station_zipcode")
    private String stationZipcode;

    @Column(name = "station_longitude", precision = 10, scale = 7)
    private BigDecimal stationLongitude;

    @Column(name = "station_latitude", precision = 10, scale = 7)
    private BigDecimal stationLatitude;

    @Column(name = "station_code")
    private Integer stationCode;

    // ── Charge payment ──────────────────────────────────────────────────────
    @Column(name = "confirm_charge_amount", precision = 15, scale = 2)
    private BigDecimal confirmChargeAmount;

    @Column(name = "payment_transaction_id")
    private String paymentTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_gateway")
    private String paymentGateway;

    @Column(name = "payment_at")
    private LocalDateTime paymentAt;

    // ── Charging session ────────────────────────────────────────────────────
    @Column(name = "charging_start_at")
    private LocalDateTime chargingStartAt;

    @Column(name = "charging_end_at")
    private LocalDateTime chargingEndAt;

    @Column(name = "charging_total_kw", precision = 10, scale = 3)
    private BigDecimal chargingTotalKw;

    // ── Session closure ─────────────────────────────────────────────────────
    @Column(name = "car_owner_end_confirmed_at")
    private LocalDateTime carOwnerEndConfirmedAt;

    @Column(name = "car_unplugged_at")
    private LocalDateTime carUnpluggedAt;

    // ── Idle time ───────────────────────────────────────────────────────────
    @Column(name = "idle_time_minutes")
    private Integer idleTimeMinutes;

    @Column(name = "idle_time_cost", precision = 15, scale = 2)
    private BigDecimal idleTimeCost;

    @Column(name = "idle_payment_transaction_id")
    private String idlePaymentTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "idle_payment_status", length = 20)
    private PaymentStatus idlePaymentStatus;

    @Column(name = "idle_payment_method")
    private String idlePaymentMethod;

    @Column(name = "idle_payment_gateway")
    private String idlePaymentGateway;

    @Column(name = "idle_payment_at")
    private LocalDateTime idlePaymentAt;

    // ── Totals ───────────────────────────────────────────────────────────────
    @Column(name = "total_charge_amount", precision = 15, scale = 2)
    private BigDecimal totalChargeAmount;

    public void populateEmptyFields(ChargeTransactionStartByCarPluggedEvent event) {
        if (carOwnerId == null) carOwnerId = event.carOwnerId();
        if (isBlank(carOwnerName)) carOwnerName = event.carOwnerName();
        if (isBlank(carOwnerPhone)) carOwnerPhone = event.carOwnerPhone();
        if (isBlank(carOwnerIdentification)) carOwnerIdentification = event.carOwnerIdentification();
        if (isBlank(carOwnerEmail)) carOwnerEmail = event.carOwnerEmail();
        if (carId == null) carId = event.carId();
        if (isBlank(carUniqueId)) carUniqueId = event.carUniqueId();
        if (isBlank(carPlate)) carPlate = event.carPlate();
        if (isBlank(carModel)) carModel = event.carModel();
        if (carPluggedAt == null) carPluggedAt = event.carPluggedAt();
        if (initialBatteryLevelInPercent == null) initialBatteryLevelInPercent = event.batteryLevel();
        if (pumpId == null) pumpId = event.pumpId();
        if (isBlank(pumpUniqueId)) pumpUniqueId = event.pumpUniqueId();
        if (isBlank(pumpName)) pumpName = event.pumpName();
        if (pumpCode == null) pumpCode = event.pumpCode();
        if (pumpKwh == null) pumpKwh = event.pumpKwh();
        if (stationId == null) stationId = event.stationId();
        if (isBlank(stationName)) stationName = event.stationName();
        if (isBlank(stationAddress)) stationAddress = event.stationAddress();
        if (isBlank(stationZipcode)) stationZipcode = event.stationZipcode();
        if (stationLongitude == null) stationLongitude = event.stationLongitude();
        if (stationLatitude == null) stationLatitude = event.stationLatitude();
        if (stationCode == null) stationCode = event.stationCode();
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public PaymentCalculateAmountEvent toPaymentCalculateAmountEvent() {
        return new PaymentCalculateAmountEvent(
                this.carOwnerId,
                this.carOwnerName,
                this.carOwnerPhone,
                this.carOwnerIdentification,
                this.carOwnerEmail,
                this.carId,
                this.carUniqueId,
                this.carPlate,
                this.carModel,
                this.carPluggedAt,
                this.initialBatteryLevelInPercent,
                this.pumpId,
                this.pumpKwh
        );
    }

    public void populateEmptyFields(ChargeStartRequest request) {
        if (isBlank(carOwnerPhone)) carOwnerPhone = request.carOwnerPhone();
        if (isBlank(carOwnerIdentification)) carOwnerIdentification = request.carOwnerIdentification();
        if (pumpId == null) pumpId = request.pumpId();
        if (isBlank(pumpUniqueId)) pumpUniqueId = request.pumpUniqueId();
        if (isBlank(pumpName)) pumpName = request.pumpName();
        if (pumpCode == null) pumpCode = request.pumpCode();
        if (stationId == null) stationId = request.stationId();
        if (isBlank(stationName)) stationName = request.stationName();
        if (isBlank(stationAddress)) stationAddress = request.stationAddress();
        if (isBlank(stationZipcode)) stationZipcode = request.stationZipcode();
        if (stationLongitude == null) stationLongitude = request.stationLongitude();
        if (stationLatitude == null) stationLatitude = request.stationLatitude();
        if (stationCode == null) stationCode = request.stationCode();
    }
}