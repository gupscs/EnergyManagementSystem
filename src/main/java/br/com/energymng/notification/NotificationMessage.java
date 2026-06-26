package br.com.energymng.notification;

public enum NotificationMessage {

    CAR_PLUG_REQUEST("Your car plug request has been received. Please proceed to the charging station.");

    private final String message;

    NotificationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
