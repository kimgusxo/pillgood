package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Notification;
import com.kit.pillgood.persistence.projection.NotificationContentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    Notification findByNotificationIndex(Long notificationIndex);
    boolean existsByNotificationIndex(Long notificationIndex);
    List<Notification> findNotificationByUser_UserIndexAndNotificationTimeAfterAndNotificationCheckFalse(Long userIndex, LocalDateTime dateTime);
    void deleteByNotificationIndex(Long notificationIndex);
    void deleteByNotificationTimeBefore(LocalDateTime localDateTime);

    @Query("select u.userIndex as userIndex, u.userFcmToken as userFcmToken, g.groupMemberName as groupMemberName, t.takePillTime as takePillTime " +
            "from TakePillCheck t " +
            "join t.takePill tp " +
            "join tp.prescription p " +
            "join p.groupMember g " +
            "join g.user u " +
            "where t.takeCheck = false and t.takeDate = :takeTime and t.takePillTime = :takePillTime")
    List<NotificationContentSummary> findNotificationContentsData(@Param("takeTime") LocalDate takeTime,
                                                                  @Param("takePillTime") int takePillTime);


}
