package br.com.energymng.charge;

public enum ChargeStatus {
    CREATED,
    STARTED,
    AMOUNT_CONFIRMED,
    PAID,
    CHARGING_START,
    CHARGING_END,
    CAR_OWNER_NOTIFIED,
    CAR_UNPLUGGED,
    FINALIZED,
    FINALIZED_WITH_ERROR,
    ERROR
}