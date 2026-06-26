package br.com.energymng.common.event.notification;

import br.com.energymng.notification.NotificationMessage;

public record CarOwnerNotificationEvent(
        String carOwnerPhone,
        String carOwnerIdentification,
        NotificationMessage message
) {}
