package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Notification;
import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository,
                               GroupMemberRepository groupMemberRepository,
                               FirebaseMessaging firebaseMessaging) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.firebaseMessaging  = firebaseMessaging;
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
        List<Notification> notifications = notificationRepository.findNotificationByUserIndexAndNotificationCheckTrue(userIndex);

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
    public NotificationDTO updateNotification(Long notificationIndex, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findByNotificationIndex(notificationIndex);

        if(notification != null) {
            notification = EntityConverter.toNotification(notificationDTO);
            notificationDTO = EntityConverter.toNotificationDTO(notificationRepository.save(notification));
            return notificationDTO;
        } else {
            return null;
        }
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
