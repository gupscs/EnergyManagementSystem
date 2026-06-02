package br.com.energymng.charge;

public record ChargingSessionCompletedEvent(Long sessionId, Long userId, Double energyKwh) {}
