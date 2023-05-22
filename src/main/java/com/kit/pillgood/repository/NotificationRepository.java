package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    Notification findByNotificationIndex(Long notificationIndex);
    boolean existsByNotificationIndex(Long notificatinoIndex);
    List<Notification> findNotificationByUser_UserIndexAndNotificationTimeAfterAndNotificationCheckFalse(Long userIndex, LocalDateTime dateTime);
    void deleteByNotificationIndex(Long notificationIndex);
    List<Notification> findNotificationsByUser_UserIndexAndNotificationCheckFalse(Long userIndex);


}
