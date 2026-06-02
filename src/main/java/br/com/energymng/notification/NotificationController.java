package br.com.energymng.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnread(@PathVariable Long userId) {
        return notificationService.findUnreadByUser(userId);
    }
}
