package br.com.energymng.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository repository;

    public Notification send(Long userId, NotificationType type, String title, String message) {
        return repository.save(Notification.builder()
            .userId(userId).type(type).title(title).message(message).read(false).build());
    }

    @Transactional(readOnly = true)
    public List<Notification> findUnreadByUser(Long userId) { return repository.findByUserIdAndReadFalse(userId); }
}
