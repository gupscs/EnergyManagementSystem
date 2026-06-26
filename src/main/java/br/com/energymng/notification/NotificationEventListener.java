package br.com.energymng.notification;

import br.com.energymng.common.event.notification.CarOwnerNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class NotificationEventListener {

    @ApplicationModuleListener
    void onCarOwnerNotification(CarOwnerNotificationEvent event) {
        String message = event.message().getMessage();
        log.info("Notification queued phone={} message={}", event.carOwnerPhone(), message);

        // TODO: Notification - send to user via WhatsApp or WebSocket UI
        // Use event.message().getMessage() to get the human-readable text for the chosen channel
    }
}
