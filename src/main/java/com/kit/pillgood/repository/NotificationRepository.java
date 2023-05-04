package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationByUserIndexAndNotificationCheckTrue(Long userIndex);
}
