package br.com.energymng.ocppgateway;

public record OcppMessageReceivedEvent(Long messageId, String stationSerial, OcppAction action, String payload) {}
