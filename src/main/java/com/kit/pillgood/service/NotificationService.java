package com.kit.pillgood.service;

import com.kit.pillgood.domain.Notification;
import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void createAllNotification() {
        // 모든 알림 생성
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public List<NotificationDTO> searchNotificationByUserIndex(Long userIndex) {
        List<Notification> notifications = notificationRepository.findNotificationsByUser_UserIndexAndNotificationCheckTrue(userIndex);

        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for(Notification notification : notifications) {
            NotificationDTO notificationDTO = EntityConverter.toNotificationDTO(notification);
            notificationDTOs.add(notificationDTO);
        }

        return notificationDTOs;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void sendAutoPushMessageNotification() {
        // 자동 메세지 전송
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void sendCreatedPrescriptionNotification(Long userIndex, Long groupMemberIndex) {
        // 처방전 알림 생성 후 전송
    }
}
